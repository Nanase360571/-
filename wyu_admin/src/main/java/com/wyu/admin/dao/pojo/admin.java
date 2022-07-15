package com.wyu.admin.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/*
 *@CLASSNAME: admin
 *AUTHOR lizhian
 */
@Data
public class admin {
    @TableId(value ="id",type = IdType.AUTO)
    private Integer id;
    private String account;
    private String password;
}
