package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.loginException.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domian.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.sqlsessionUtil;


import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = sqlsessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        System.out.println("正在运行");
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        System.out.println(loginAct+"00000000000"+loginPwd);
        User user = userDao.login(map);
        if (user == null){
            throw new LoginException("用户名密码错误");
        }

        return user;
    }
}
