package com.polysoft.framework.Shared.Packets;


/**
 * Packet used for testing.
 * @author totoro987123
 * @version 1.0
 * @since January 14, 2020
 */
public class TestPacket2 extends Packet {
    
    // STATIC FIELDS
    /**
     * ID used to verify if packets can be serialized / deserialized with eachother.
     */
    private static final long serialVersionUID = 5412483968602157948L;

    /**
     * String used to pass messages between the server and the cleint.
     */
    private String string;




    // CONSTRUCTORS

    /**
     * Default constructor.
     * @param string Mssage to be passed between the server and client.
     */
    public TestPacket2(String string) {
        this.string = string;
    }




    // PUBLIC METHODS

    /**
     * Gets the string message of this packet.
     * @return String The message being stored in this packet.
     */
    public String getString() {
        return this.string;
    }
}
