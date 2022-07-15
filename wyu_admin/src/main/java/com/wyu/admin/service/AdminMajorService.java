package com.wyu.admin.service;/*
 *@interface: AdminMajorService
 *AUTHOR lizhian
 */

import com.wyu.common.dao.pojo.major;
import com.wyu.common.vo.Result;

public interface AdminMajorService {
    Result addAdminMajor(major major);
}
