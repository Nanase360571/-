package com.wyu.tea.vo.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:targetParam
 * @Description:
 * @author:Aan
 * @data 2022/3/13 22:43
 **/
@Data
public class targetParam {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String tarContent;
    private Integer proportion;
}
