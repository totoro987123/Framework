package com.polysoft.framework.Server.Services;

import com.github.thorbenkuck.netcom2.network.shared.Session;
import com.polysoft.framework.Server.Annotations.RemoteMethod;
import com.polysoft.framework.Shared.Service;
import com.polysoft.framework.Shared.Packets.TestPacket;

public class TestService2 extends Service {
    
    public TestService2(String name) {
        super(name);
    }

    @RemoteMethod()
    private void remoteTest(TestPacket testPacket, Session session) {
        System.out.println(testPacket.getString() + " DOF");
    }

    public void noob() {
        System.out.println("NOOB!!");
    }
}
