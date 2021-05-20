package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domian.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.SqlsessionFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path =request.getServletPath();
        if ("/settings/user/login.do".equals(path)){
            login(request,response);
        }else if ("".equals(path)){
        }
    }


    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = (UserService) SqlsessionFactory.getService(new UserServiceImpl());
        //获取用户名，密码，ip
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        try {
            User user = userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user", user);
            //成功登录 返回 "success":"true"
            PrintJson.printJsonFlag(response, true);
        } catch (Exception e) {
            e.printStackTrace();
            //登录失败 返回 "success":"true","msg":msg
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);

            PrintJson.printJsonObj(response,map );
        }
    }
}