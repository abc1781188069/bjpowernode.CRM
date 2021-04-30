package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.loginException.LoginException;
import com.bjpowernode.crm.settings.domian.User;



public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
