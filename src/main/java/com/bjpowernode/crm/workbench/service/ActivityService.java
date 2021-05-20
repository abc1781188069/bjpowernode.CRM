package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Boolean save(Activity a);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    Boolean deletes(String[] id);


    Map<String, Object> getUser(String id);

    Activity detail(String id);

    List<ActivityRemark> showRemarklist(String activityId);
}
