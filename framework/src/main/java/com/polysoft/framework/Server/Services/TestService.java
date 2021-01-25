package com.polysoft.framework.Server.Services;

import com.polysoft.framework.Shared.Annotations.RemoteMethod;
import com.polysoft.framework.Shared.User;
import com.polysoft.framework.Shared.Annotations.AnnotationProcessor;
import com.polysoft.framework.Shared.Interfaces.Service;
import com.polysoft.framework.Shared.Annotations.ServiceVariable;
import com.polysoft.framework.Shared.Packets.TestPacket;

public class TestService implements Service {
    
    @ServiceVariable
    private TestService2 testService2;

    public TestService() {
    
    }

    @RemoteMethod()
    private void remoteTest(TestPacket testPacket, User user) {
        System.out.println(testPacket.getString());
        testService2.noob();

        System.out.println("Loging in");
        user.authorize(true);
    }

    public void boop() {
        System.out.println("boop");
        //testService2.noob();
    }

    @Override
    public void initialize() {
        new Test3();

        AnnotationProcessor.applyRemoteAnnotations(this);
        AnnotationProcessor.applyServiceAnnotations(this);
    }
}