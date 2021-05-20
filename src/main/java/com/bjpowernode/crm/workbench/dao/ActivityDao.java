package com.bjpowernode.crm.workbench.dao;


import com.bjpowernode.crm.workbench.domain.Activity;
import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity a);

    int getTotal(Map<String, Object> map);

    List<Activity> getActivity(Map<String, Object> map);

    int deletes(String[] id);

    Activity getUser(String id);

    Activity detail(String id);
}
