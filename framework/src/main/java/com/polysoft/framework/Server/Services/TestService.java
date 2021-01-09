package com.polysoft.framework.Server.Services;

import com.github.thorbenkuck.netcom2.network.shared.Session;
import com.polysoft.framework.Server.Annotations.RemoteMethod;
import com.polysoft.framework.Shared.Service;
import com.polysoft.framework.Shared.Annotations.ServiceVariable;
import com.polysoft.framework.Shared.Packets.TestPacket;

public class TestService extends Service {
    
    @ServiceVariable(serviceName = "TestService2")
    private TestService2 ts2;

    public TestService(String name) {
        super(name);
    }

    @RemoteMethod()
    private void remoteTest(TestPacket testPacket, Session session) {
        System.out.println(testPacket.getString());
        ts2.noob();
    }
}
