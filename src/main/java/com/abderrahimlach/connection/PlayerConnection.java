package com.abderrahimlach.connection;

import com.abderrahimlach.MinecraftServer;
import com.abderrahimlach.protocol.Packet;
import com.abderrahimlach.protocol.Protocol;
import com.abderrahimlach.protocol.packets.HandshakePacket;
import com.abderrahimlach.protocol.packets.PingRequestPacket;
import com.abderrahimlach.protocol.packets.PingRespondPacket;
import com.abderrahimlach.protocol.packets.StatusRespondPacket;
import com.abderrahimlach.protocol.type.Direction;
import com.abderrahimlach.util.ByteReader;
import com.abderrahimlach.util.DataUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerConnection {

    private Socket socket;
    private int state;
    private boolean acceptData = true;
    private MinecraftServer ms = MinecraftServer.getInstance();

    public PlayerConnection(Socket socket) throws IOException {
        this.socket = socket;

        ms.warn("Accepted connection from " + socket.getInetAddress().toString());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        while (acceptData) {
            int size = DataUtil.readUnsignedVarInt(inputStream);
            byte[] buffer = new byte[size];
            inputStream.readFully(buffer);
            ByteReader reader = new ByteReader(buffer);
            int id = DataUtil.readUnsignedVarInt(reader);

            Packet packet = Protocol.getPacket(state, Direction.SERVER_SIDE, reader, id);
            handlePacket(packet);
        }

    }

    public Socket getSocket() {
        return socket;
    }

    private void handlePacket(Packet packet) {
        if (state == 0) {
            if (packet.getId() == 0x00) {
                HandshakePacket handshakePacket = (HandshakePacket) packet;
                this.state = handshakePacket.getNextState();
            }
        } else if (state == 1) {
            if (packet.getId() == 0x00) {
                ms.warn("Got status request");

                StatusRespondPacket response = new StatusRespondPacket("1.8.9", 47, 500, 5, "§bHello there this is what we call §3§lMOTD§b!");
                sendData(response.encode());
            } else if (packet.getId() == 0x01) {
                ms.warn("Successfully received ping request");
                PingRequestPacket pingRequestPacket = (PingRequestPacket) packet;
                PingRespondPacket response = new PingRespondPacket(pingRequestPacket.getLongBytes());
                sendData(response.encode());
            }
        } /*
        else if (state == 2) {
            if (packet.getId() == 0x00) {
                ms.warn("Login start packet");
                LoginStartPacket loginStartPacket = (LoginStartPacket) packet;
                ms.warn("Found name: " + loginStartPacket.getName());

                LoginDisconnectPacket loginDisconnectPacket = new LoginDisconnectPacket("§4This server doesn't support logging in,§d " + loginStartPacket.getName());
                sendData(loginDisconnectPacket.encode());
            }
        }*/
    }

    private void sendData(byte[] data) {
        byte[] dataLength = DataUtil.intToUnsignedVarInt(data.length);

        write(dataLength);
        write(data);
    }

    private void write(byte[] data) {
        try {
            socket.getOutputStream().write(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setState(int state) {
        this.state = state;
    }

    private int getState() {
        return state;
    }

    public void close() throws IOException {
        acceptData = false;
        socket.close();
    }

}
