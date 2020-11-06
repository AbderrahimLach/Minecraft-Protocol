package com.abderrahimlach.protocol;

public abstract class Incoming implements Packet {

    private byte[] bytes;

    public Incoming(byte[] bytes) {
        this.bytes = bytes;
    }

    public abstract int getId();

    public byte[] getData() {
        return bytes;
    }


}
