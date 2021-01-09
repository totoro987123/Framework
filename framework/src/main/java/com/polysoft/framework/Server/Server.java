package com.polysoft.framework.Server;

import com.github.thorbenkuck.netcom2.exceptions.ClientConnectionFailedException;
import com.github.thorbenkuck.netcom2.exceptions.StartFailedException;
import com.github.thorbenkuck.netcom2.logging.Logging;
import com.github.thorbenkuck.netcom2.network.server.RemoteObjectRegistration;
import com.github.thorbenkuck.netcom2.network.server.ServerStart;
import com.github.thorbenkuck.netcom2.network.shared.CommunicationRegistration;
import com.polysoft.framework.Server.Services.*;
import com.polysoft.framework.Shared.Game;
import com.polysoft.framework.Shared.Service;

public class Server extends Game implements Runnable {

    private static Service[] services = {
        new TestService("TestService"),
        new TestService2("TestService2")
    };
    
    private ServerStart serverStart;
    private int port;
    private CommunicationRegistration communicationRegistration;
    private RemoteObjectRegistration ojectRegistration;


    public Server(int port) {
        super(services);

        this.port = port;
        this.serverStart = ServerStart.at(this.port);

        serverStart.setLogging(Logging.trace());


        this.communicationRegistration = this.serverStart.getCommunicationRegistration();
        this.ojectRegistration = RemoteObjectRegistration.open(this.serverStart);
    }

    public void open() {
        try {
            this.serverStart.launch();
        } catch (StartFailedException e) {
            e.printStackTrace();
        }

        new Thread(this).start();
    }

    public CommunicationRegistration getCommunicationRegistration() {
        return this.communicationRegistration;
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
