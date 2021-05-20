package com.bjpowernode.crm.utils;

public class SqlsessionFactory {
    public static Object getService(Object service){
        return new UserInvocationHandler(service).getProxy();
    }
}
