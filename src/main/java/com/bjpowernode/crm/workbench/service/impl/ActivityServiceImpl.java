package com.bjpowernode.crm.workbench.service.impl;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domian.User;
import com.bjpowernode.crm.utils.SqlsessionUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private final ActivityDao activityDao = SqlsessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private final ActivityRemarkDao activityRemarkDao = SqlsessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private final UserDao userDao = SqlsessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public Boolean save(Activity a) {
        System.out.println("zhengzaiyunx1");
        Boolean flag = true;
        int count = activityDao.save(a);
        if (count != 1 ){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String,Object> map) {
        System.out.println("54887877777777777777777777777777");
        int total= activityDao.getTotal(map);
        List<Activity> dataList= activityDao.getActivity(map);
        PaginationVo<Activity> paginationVo = new PaginationVo<>();
        paginationVo.setTotal(total);
        paginationVo.setDataList(dataList);


        return paginationVo;
    }

    @Override
    public Boolean deletes(String[] id) {
        Boolean flag = true;
        int coun1 = activityRemarkDao.deletes(id);
        if (coun1 == 0){
            flag = false;
        }
        int coun2 = activityDao.deletes(id);
        if (coun2 == 0){
            flag = false;
        }



        return flag;
    }

    @Override
    public Map<String, Object> getUser(String id) {
        Map<String, Object> map =new HashMap<>();
        List<User> uList = userDao.getUserList();
        Activity a =activityDao.getUser(id);
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public Activity detail(String id) {


        Activity activity = activityDao.detail(id);
        System.out.println("123333333333333333333");



        return activity;
    }

    @Override
    public List<ActivityRemark> showRemarklist(String activityId) {
        List<ActivityRemark> activityRemark = activityRemarkDao.showRemarklist(activityId);
        return activityRemark;
    }

}
