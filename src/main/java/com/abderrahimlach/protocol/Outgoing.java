package com.abderrahimlach.protocol;

public abstract class Outgoing implements Packet {
    public abstract byte[] encode();
    public abstract int getId();
}
