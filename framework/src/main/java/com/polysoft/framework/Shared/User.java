package com.polysoft.framework.Shared;

import com.github.thorbenkuck.netcom2.network.shared.Session;

public class User {
    
    private Session userSession;

    public User(Session userSession) {
        this.userSession = userSession;
    }

    public void send(Object o) {
        this.userSession.send(o);
    }

    public void authorize(boolean bool) {
        this.userSession.setIdentified(bool);
    }

    public boolean isAuthorized() {
        return this.userSession.isIdentified();
    }
}
