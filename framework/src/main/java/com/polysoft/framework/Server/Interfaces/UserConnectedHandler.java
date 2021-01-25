package com.polysoft.framework.Server.Interfaces;

import com.polysoft.framework.Shared.User;

@FunctionalInterface
public interface UserConnectedHandler {

    // METHODS
    void connected(User user);
}
