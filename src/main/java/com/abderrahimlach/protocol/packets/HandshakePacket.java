package com.abderrahimlach.protocol.packets;

import com.abderrahimlach.protocol.Incoming;
import com.abderrahimlach.util.ByteReader;
import com.abderrahimlach.util.DataUtil;

public class HandshakePacket extends Incoming {

    private final int protocolVersion;
    private final String serverAddress;
    private final int serverPort;
    private final int nextState;

    public HandshakePacket(ByteReader reader) {
        super(reader.getData());

        protocolVersion = DataUtil.readUnsignedVarInt(reader);
        serverAddress = DataUtil.readString(reader);
        serverPort = DataUtil.readUnsignedShort(reader);
        nextState = DataUtil.readUnsignedVarInt(reader);
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getNextState() {
        return nextState;
    }

    @Override
    public int getId() {
        return 0x00;
    }
}