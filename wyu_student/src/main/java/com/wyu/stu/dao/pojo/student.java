package com.wyu.stu.dao.pojo;

import lombok.Data;

/**
 * @PackageName:com.wyu.stu.dao
 * @ClassName:student
 * @Description:
 * @author:Aan
 * @data 2022/1/18 18:38
 **/
@Data
public class student {
    private Integer id;
    private String account;
    private String password;
    private String name;
    private Integer claNo;
}
