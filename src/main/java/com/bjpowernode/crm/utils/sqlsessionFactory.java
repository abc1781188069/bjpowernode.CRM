package com.bjpowernode.crm.utils;

public class sqlsessionFactory {
    public static Object getService(Object service){
        return new UserInvocationHandler(service).getProxy();
    }
}
