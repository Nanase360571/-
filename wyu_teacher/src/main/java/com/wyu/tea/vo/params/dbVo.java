package com.wyu.tea.vo.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:dbVo
 * @Description:
 * @author:Aan
 * @data 2022/3/22 9:45
 **/
@Data
public class dbVo {
    private Integer id;
    private Integer dbType;
    private String dbContent;
    private String dbAnswer;
    private String dbReallyType;

}
