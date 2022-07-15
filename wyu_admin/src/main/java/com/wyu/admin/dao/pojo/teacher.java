package com.wyu.admin.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.stu.dao.pojo
 * @ClassName:teacher
 * @Description:
 * @author:Aan
 * @data 2022/1/24 13:01
 **/
@Data
public class teacher {
    @TableId(value ="id",type = IdType.AUTO)
    private Integer id;
    private String account;
    private String password;
    private String name;
}
