package com.polysoft.framework;

import com.polysoft.framework.Server.Server;
import com.polysoft.framework.Shared.Game;
import com.polysoft.framework.Shared.Service;
import com.polysoft.framework.Shared.Packets.TestPacket;
import com.polysoft.framework.Client.Client;
/**
 * Hello world!
 */
public final class App {

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        int port = 4447;
        String address = "localhost";

        Server server = new Server(port);
        server.start();

        Client client = new Client(address, port);
        client.connect();

        client.send(new TestPacket("BOOOPP"));

    }
}
