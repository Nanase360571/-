package com.wyu.tea.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.tea.dao.pojo
 * @ClassName:teacher
 * @Description:
 * @author:Aan
 * @data 2022/1/18 20:51
 **/
@Data
public class teacher {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String account;
    private String password;
    private String name;
}
