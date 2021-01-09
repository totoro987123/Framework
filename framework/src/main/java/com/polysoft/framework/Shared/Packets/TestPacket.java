package com.polysoft.framework.Shared.Packets;

public class TestPacket extends Packet {
    
    private String string;

    public TestPacket(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }
}
