package com.polysoft.framework.Server.Services;

import com.github.thorbenkuck.netcom2.network.shared.Session;
import com.polysoft.framework.Shared.Annotations.AnnotationProcessor;
import com.polysoft.framework.Shared.Annotations.RemoteMethod;
import com.polysoft.framework.Shared.Annotations.ServiceVariable;
import com.polysoft.framework.Shared.Interfaces.Service;
import com.polysoft.framework.Shared.Packets.TestPacket;

public class Test3 {

    @ServiceVariable
    private TestService2 testService2;
    
    public Test3() {
        System.out.println("MADE");
        AnnotationProcessor.applyRemoteAnnotations(this);
        AnnotationProcessor.applyServiceAnnotations(this);
    }

    @RemoteMethod()
    private void remoteTest(TestPacket testPacket, Session session) {
        System.out.println("BAN BAN BAN");
        testService2.noob();
        testService2.noob();
        testService2.noob();
        testService2.noob();
    }
}
