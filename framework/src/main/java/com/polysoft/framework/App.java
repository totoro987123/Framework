package com.polysoft.framework;

import com.polysoft.framework.Server.Server;
import com.polysoft.framework.Shared.Game;
import com.polysoft.framework.Shared.Interfaces.Service;
import com.polysoft.framework.Shared.Packets.TestPacket;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.polysoft.framework.Client.Client;
import com.polysoft.framework.Server.Services.*;

/**
 * Hello world!
 */
public final class App {

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        int port = 4448;
        String address = "localhost";

        Server server = new Server(port);
        server.start();

        ((TestService) server.getService("TestService")).boop();

        Client client = new Client(address, port);
        client.connect();

        client.send(new TestPacket("BOOOPP"));

    }
}
