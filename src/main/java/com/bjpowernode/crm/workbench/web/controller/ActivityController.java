package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domian.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.*;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(req,resp);
        }else if("/workbench/activity/save.do".equals(path)){
            save(req,resp);
        }else if("/workbench/activity/pageList.do".equals(path)){
            pageList(req,resp);
        }else if("/workbench/activity/deletes.do".equals(path)){
            deletes(req,resp);
        }else if("/workbench/activity/getUser.do".equals(path)){
            getuser(req,resp);
        }else if("/workbench/activity/detail.do".equals(path)){
            detail(req,resp);
        }else if("/workbench/activity/showRemarklist.do".equals(path)){
            showRemarklist(req,resp);
        }
        
    }

    private void showRemarklist(HttpServletRequest req, HttpServletResponse resp) {
        String activityId = req.getParameter("activityId");
        ActivityService activityService = (ActivityService) SqlsessionFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> activityRemark = activityService.showRemarklist(activityId);
        PrintJson.printJsonObj(resp,activityRemark);
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ActivityService activityService = (ActivityService) SqlsessionFactory.getService(new ActivityServiceImpl());
        Activity activity = activityService.detail(id);
        req.setAttribute("activity",activity);
        req.getRequestDispatcher("/workbench/activity/detail.jsp").forward(req,resp);



    }

    private void getuser(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("取得用户信息列表");
        String id = req.getParameter("id");
        ActivityService activityService = (ActivityService) SqlsessionFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map = activityService.getUser(id);
        PrintJson.printJsonObj(resp,map);
    }

    private void deletes(HttpServletRequest req, HttpServletResponse resp) {
        String id[] = req.getParameterValues("id");
        ActivityService activityService = (ActivityService) SqlsessionFactory.getService(new ActivityServiceImpl());
        Boolean flag = activityService.deletes(id);
        PrintJson.printJsonFlag(resp,flag);
    }


    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String owner = req.getParameter("owner");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String pageSizes = req.getParameter("pageSize");
        String pageNos = req.getParameter("pageNo");
        int pageSize=Integer.valueOf(pageSizes);
        int pageNo= Integer.valueOf(pageNos);
        pageNo = (pageNo-1)*pageSize;
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageSize",pageSize);
        map.put("pageNo",pageNo);
        ActivityService activityService = (ActivityService) SqlsessionFactory.getService(new ActivityServiceImpl());

        PaginationVo<Activity> vo = activityService.pageList(map);
        PrintJson.printJsonObj(resp,vo);


    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        String createTime= DateTimeUtil.getDate();
        String createBy= ((User)req.getSession().getAttribute("user")).getName();
        ActivityService activityService = (ActivityService) SqlsessionFactory.getService(new ActivityServiceImpl());
        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);
        Boolean flag = activityService.save(a);
        PrintJson.printJsonFlag(resp,flag);

    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("取得用户信息列表");
        UserService userService = (UserService) SqlsessionFactory.getService(new UserServiceImpl());
        List<User> list= userService.getUserList();
        PrintJson.printJsonObj(resp,list);
    }
}
