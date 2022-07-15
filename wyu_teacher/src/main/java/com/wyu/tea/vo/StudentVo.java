package com.wyu.tea.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wyu.tea.vo.params.DbResultVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 *@CLASSNAME: StudentVo
 *AUTHOR lizhian
 */
@Data

public class StudentVo {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String account;
    private String password;
    private String name;
    private String claNo;
    List<DbResultVo> singleDbResultVos = new ArrayList<>();
    List<DbResultVo> multiDbResultVos = new ArrayList<>();
    List<DbResultVo> judgeDbResultVos = new ArrayList<>();
    String singleDbResult ;
    String multiDbResult ;
    String judgeDbResult ;
}
