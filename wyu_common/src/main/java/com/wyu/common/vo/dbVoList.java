package com.wyu.common.vo;

import lombok.Data;

import java.util.ArrayList;

/**
 * @PackageName:com.wyu.common.vo
 * @ClassName:dbVoList
 * @Description:
 * @author:Aan
 * @data 2022/1/19 1:21
 **/
@Data
public class dbVoList {
    private ArrayList singleChoiceArrayList;
    private ArrayList multipleChoiceArrayList;
    private ArrayList tfArrayList;
}
