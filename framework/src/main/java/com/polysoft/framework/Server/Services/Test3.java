package com.polysoft.framework.Server.Services;

import com.polysoft.framework.Shared.User;
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

    @RemoteMethod(login = true)
    private void remoteTest(TestPacket testPacket, User user) {
        System.out.println("BAN BAN BAN");
        testService2.noob();
        testService2.noob();
        testService2.noob();
        testService2.noob();
    }
}
