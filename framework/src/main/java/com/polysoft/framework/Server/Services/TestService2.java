package com.polysoft.framework.Server.Services;

import com.github.thorbenkuck.netcom2.network.shared.Session;
import com.polysoft.framework.Shared.Annotations.RemoteMethod;
import com.polysoft.framework.Shared.AnnotationProcessor;
import com.polysoft.framework.Shared.Service;
import com.polysoft.framework.Shared.Annotations.ServiceVariable;
import com.polysoft.framework.Shared.Interfaces.RemoteEvent;
import com.polysoft.framework.Shared.Packets.TestPacket;

public class TestService2 extends Service implements RemoteEvent {
    
    @ServiceVariable
    private TestService ts;

    public TestService2() {
    }

    @RemoteMethod()
    private void remoteTest(TestPacket testPacket, Session session) {
        System.out.println(testPacket.getString() + " DOF");
        ts.boop();
    }

    public void noob() {
        System.out.println("NOOB!!");
        //ts.boop();
    }

    @Override
    public void initialize() {
        System.out.println("BOOTING UP GOOD SIR!");
    }
}
