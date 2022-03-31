package com.wyu.tea.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String account;
    private String password;
    private String name;
    private String claNo;
}
