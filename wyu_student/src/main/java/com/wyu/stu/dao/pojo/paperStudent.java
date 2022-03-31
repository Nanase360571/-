package com.wyu.stu.dao.pojo;

import lombok.Data;

/**
 * @PackageName:com.wyu.stu.dao.pojo
 * @ClassName:Paper_Student
 * @Description:
 * @author:Aan
 * @data 2022/1/21 23:18
 **/
@Data
public class paperStudent {
    private Integer id;
    private Integer psStudent;
    private Integer psPaper;
    private String paperName;
    private String paperStart;
    private String paperEnd;
}
