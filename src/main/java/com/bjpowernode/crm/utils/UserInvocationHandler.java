package com.bjpowernode.crm.utils;


import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserInvocationHandler implements InvocationHandler {
    private Object target;
    public UserInvocationHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SqlSession session =null;
        Object obj = null;
        try{
            session = UserUtil.getSqlSession();
            obj = method.invoke(target,args);
            session.commit();
        }catch (Exception e){
            session.rollback();
            e.printStackTrace();
        }finally {
            UserUtil.sqlClose(session);
        }
        return obj;
    }
    public Object getProxy(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }
}
