package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int deletes(String[] id);

    List<ActivityRemark> showRemarklist(String activityId);
}
