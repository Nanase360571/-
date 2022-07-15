package com.wyu.tea.vo;

import lombok.Data;

import java.util.List;

@Data
public class InnerDbVo {
    private List<String> dbAnswer;
    private List<String> dbOption;
    private String dbTitle;
    private Integer dbType;

}
