package com.bjpowernode.crm.utils;

public class UserFactory {
    public static Object getService(Object service){
        return new UserInvocationHandler(service).getProxy();
    }
}
