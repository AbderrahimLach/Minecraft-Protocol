package com.abderrahimlach.protocol.packets;

import com.abderrahimlach.protocol.Incoming;
import com.abderrahimlach.util.ByteReader;

import java.util.Arrays;

public class PingRequestPacket extends Incoming {

    private byte[] longBytes;

    public PingRequestPacket(ByteReader reader) {
        super(reader.getData());

        this.longBytes = Arrays.copyOfRange(reader.getData(), 1, getData().length); // this should always be 8 bytes
    }

    /**
     * The bytes that represent the long sent from the Minecraft client
     * @return the bytes in an array
     */
    public byte[] getLongBytes() {
        return longBytes;
    }

    public int getId() {
        return 0x01;
    }
}
