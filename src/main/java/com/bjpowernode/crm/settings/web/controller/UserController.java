package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.loginException.LoginException;
import com.bjpowernode.crm.settings.domian.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.sqlsessionFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入控制器");
        String port = req.getServletPath();
        if ("/settings/web/controller.do".equals(port)){
            login(req,resp);

        }

    }

    private void login(HttpServletRequest req, HttpServletResponse resp){

        String loginAct=req.getParameter("loginAct");
        String loginPwd=req.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = req.getRemoteAddr();
        System.out.println(ip+"......................................................");
        UserService userService = (UserService) sqlsessionFactory.getService(new UserServiceImpl());
        try{
            User user = userService.login(loginAct,loginPwd ,ip);
            req.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(resp,true);
        }catch (LoginException e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(resp,map);
        }
    }


}
