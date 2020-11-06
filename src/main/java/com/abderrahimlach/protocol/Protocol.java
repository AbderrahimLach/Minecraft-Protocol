package com.abderrahimlach.protocol;

import com.abderrahimlach.protocol.packets.HandshakePacket;
import com.abderrahimlach.protocol.packets.PingRequestPacket;
import com.abderrahimlach.protocol.packets.StatusRequestPacket;
import com.abderrahimlach.protocol.type.Direction;
import com.abderrahimlach.util.ByteReader;

public class Protocol {

    public static Packet getPacket(int state, Direction direction, ByteReader reader, int id) {
        if (state == 0) {
            if (direction == Direction.SERVER_SIDE) {
                if (id == 0x00) {
                    return new HandshakePacket(reader);
                }
            }
        } else if (state == 1) {
            if (direction == Direction.SERVER_SIDE) {
                if (id == 0x00) {
                    return new StatusRequestPacket(reader);
                } else if (id == 0x01) {
                    return new PingRequestPacket(reader);
                }
            }
        } /*
        else if (state == 2) {
            if (direction == Direction.SERVER_SIDE) {
                if (id == 0x00) {
                    return new LoginStartPacket(reader);
                }
            }
        }
        */

        return null;
    }
}
