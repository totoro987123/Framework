package com.polysoft.framework.Client;

import com.github.thorbenkuck.netcom2.exceptions.StartFailedException;
import com.github.thorbenkuck.netcom2.network.client.ClientStart;
import com.github.thorbenkuck.netcom2.network.client.RemoteObjectFactory;
import com.github.thorbenkuck.netcom2.network.client.Sender;
import com.github.thorbenkuck.netcom2.network.shared.CommunicationRegistration;
import com.polysoft.framework.Shared.Game;

public class Client extends Game {

    public static CommunicationRegistration clientCommunicationRegistration;
    public static RemoteObjectFactory remoteObjectFactory;

    private ClientStart clientStart;

    public Client(String address, int port) {
        super(false);
        this.clientStart = ClientStart.at(address, port);

        clientCommunicationRegistration = this.clientStart.getCommunicationRegistration();
        remoteObjectFactory = RemoteObjectFactory.open(this.clientStart);

    }

    public void connect() {
        try 
        {
            this.clientStart.launch();
        } catch (StartFailedException e) {
            e.printStackTrace();
        }
    }

    public void send(Object object) {
        Sender.open(clientStart)
            .objectToServer(object);

    }
}
