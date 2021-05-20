package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.loginException.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domian.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlsessionUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlsessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd,String ip) throws LoginException {
        Map<String,String> map = new HashMap<String,String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        System.out.println(ip+"11111111111");
        User user = userDao.login(map);
        if(user == null){
            throw new LoginException("账号密码错误");
        }
        //验证expireTime登录有效期
        if(user.getExpireTime().compareTo(DateTimeUtil.getDate())<0) {
            throw new LoginException("登录超时");
        }
        //验证登录状态
        if (user.getLockState().equals("0")){
            throw  new LoginException("处于不可登录状态") ;
        }
        //验证ip受限
        if (!user.getAllowIps().contains(ip)){
            throw  new LoginException("IP受限") ;
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> list = userDao.getUserList();
        return list;
    }
}