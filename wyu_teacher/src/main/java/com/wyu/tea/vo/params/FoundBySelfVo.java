package com.wyu.tea.vo.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wyu.common.vo.dbVo;
import lombok.Data;

import java.util.List;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:FoundBySelf
 * @Description:
 * @author:Aan
 * @data 2022/3/22 9:46
 **/
@Data
public class FoundBySelfVo {
    private Integer teacherId;
    private Integer courseId;
    private String paperName;
    private Integer paperSum;
    private String startTime;
    private String endTime;
    private Integer multiNumber;
    private Integer singleNumber;
    private Integer judgeNumber;
    private Integer singleSum;
    private Integer judgeSum;
    private Integer multiSum;
    private Integer papFound;
    private String examTime;
    private List<dbVo> dbList;
}
