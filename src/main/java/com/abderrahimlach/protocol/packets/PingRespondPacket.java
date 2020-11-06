package com.abderrahimlach.protocol.packets;

import com.abderrahimlach.protocol.Outgoing;
import com.abderrahimlach.util.DataUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PingRespondPacket extends Outgoing {

    private byte[] longBytes;

    public PingRespondPacket(byte[] longBytes) {
        this.longBytes = longBytes;
    }

    /**
     * This produces a byte array that starts with the packet id and ends with the
     * long (in bytes) from the client
     * @return byte array
     */
    @Override
    public byte[] encode() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            outputStream.write(DataUtil.intToUnsignedVarInt(getId()));
            outputStream.write(longBytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    @Override
    public int getId() {
        return 0x01;
    }
}