package com.polysoft.framework.Server;

import com.github.thorbenkuck.netcom2.exceptions.ClientConnectionFailedException;
import com.github.thorbenkuck.netcom2.exceptions.StartFailedException;
import com.github.thorbenkuck.netcom2.network.server.RemoteObjectRegistration;
import com.github.thorbenkuck.netcom2.network.server.ServerStart;
import com.polysoft.framework.Shared.Game;

/**
 * Server class for handling server-side functions.
 */
public class Server extends Game implements Runnable {
    
    // INSTANCE FIELDS

    /**
     * Serverstart instance.
     */
    private ServerStart serverStart;

    /**
     * The value for the port that the server is listening on.
     */
    private int port;

    /**
     * The remoteObjectRegistration object, which is used to establish remote interfaces.
     */
    private RemoteObjectRegistration objectRegistration;




    // CONSTRUCTORS

    /**
     * Default constructor.
     * @param port The port on which to establish the server.
     */
    public Server(int port) {
        super(true);

        this.port = port;
        this.serverStart = ServerStart.at(this.port);

        this.setCommunicationRegistration(this.serverStart.getCommunicationRegistration());
        this.objectRegistration = RemoteObjectRegistration.open(this.serverStart);
    }




    // PUBLIC METHODS

    /**
     * Opens the server on the given port.
     */
    public void open() {
        try {
            this.serverStart.launch();
        } catch (StartFailedException e) {
            e.printStackTrace();
        }

        new Thread(this).start();
    }


    /**
     * Runs the server and listens for client connections.
     */
    @Override
    public void run() {
        System.out.println("Server is now running at port " + port + ".");

        this.serverStart.addClientConnectedHandler((client) -> {
            System.out.println("New connection");

            //Player leaving here
        });

        try {
            this.serverStart.acceptAllNextClients();
        } catch (ClientConnectionFailedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Starts the server.
     */
    public void start() {
        this.load();
        this.open();
    }


    /**
     * Closes the server.
     * ![BROKEN] Fix and test this method.
     */
    public void close() {
        this.serverStart.disconnect();
    }

}
