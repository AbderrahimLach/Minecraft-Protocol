package com.abderrahimlach.protocol.packets;

import com.abderrahimlach.protocol.Incoming;
import com.abderrahimlach.util.ByteReader;

public class StatusRequestPacket extends Incoming {

    public StatusRequestPacket(ByteReader reader) {
        super(reader.getData());
    }

    public int getId() {
        return 0x00;
    }
}