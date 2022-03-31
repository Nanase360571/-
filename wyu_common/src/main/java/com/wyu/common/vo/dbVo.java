package com.wyu.common.vo;

import lombok.Data;

import java.util.ArrayList;

/**
 * @PackageName:com.wyu.common.vo
 * @ClassName:dbVo
 * @Description:
 * @author:Aan
 * @data 2022/1/18 23:37
 **/
@Data
public class dbVo {
    private int id;
    private int dbType;
    private String title;
    private ArrayList<String> option;
    private ArrayList<String> answer;
    private ArrayList<String> stuAnswer;

}
