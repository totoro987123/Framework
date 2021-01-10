package com.polysoft.framework.Server;

import com.github.thorbenkuck.netcom2.exceptions.ClientConnectionFailedException;
import com.github.thorbenkuck.netcom2.exceptions.StartFailedException;
import com.github.thorbenkuck.netcom2.network.server.RemoteObjectRegistration;
import com.github.thorbenkuck.netcom2.network.server.ServerStart;
import com.github.thorbenkuck.netcom2.network.shared.CommunicationRegistration;
import com.polysoft.framework.Shared.Game;

public class Server extends Game implements Runnable {
    
    private ServerStart serverStart;
    private int port;
    private RemoteObjectRegistration objectRegistration;

    public Server(int port) {
        super(true);

        this.port = port;
        this.serverStart = ServerStart.at(this.port);

        this.setCommunicationRegistration(this.serverStart.getCommunicationRegistration());
        this.objectRegistration = RemoteObjectRegistration.open(this.serverStart);
    }

    public void open() {
        try {
            this.serverStart.launch();
        } catch (StartFailedException e) {
            e.printStackTrace();
        }

        new Thread(this).start();
    }

    @Override
	public void run() {
        System.out.println("Server is now running at port " + port + ".");

        this.serverStart.addClientConnectedHandler((client) -> 
        {
            System.out.println("New connection");

            //Player leaving here
        });

        try 
        {
            this.serverStart.acceptAllNextClients();
        } catch (ClientConnectionFailedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.load();
        RemoteMethodProcessor.processRemoteMethods(this, this.getServiceArray());
        this.open();
    }

}
