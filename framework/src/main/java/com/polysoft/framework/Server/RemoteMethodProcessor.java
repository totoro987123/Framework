package com.polysoft.framework.Server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.polysoft.framework.Server.Annotations.RemoteMethod;
import com.polysoft.framework.Shared.Service;
import com.polysoft.framework.Shared.Packets.TestPacket;

public final class RemoteMethodProcessor {
    private RemoteMethodProcessor() {
    }

    public static void processRemoteMethods(final Server server, final Service[] services) {
        for (Service service : services) {
            Class<?> clazz = service.getClass();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(RemoteMethod.class)) {
                    System.out.println("PRESENT");

                    method.setAccessible(true);
                    Class<?> type = method.getParameterTypes()[0];

                    
                    System.out.println(type);
                    System.out.println(TestPacket.class);
                    server.getCommunicationRegistration()
                        .register(type)
                        .addFirst((Session, Object) -> {
                            System.out.println("HERE");
                            try {
                                method.invoke(service, Object, Session);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
                }
            }
        }                       
    }
}
