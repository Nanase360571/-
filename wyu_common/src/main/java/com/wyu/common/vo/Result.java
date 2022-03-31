package com.wyu.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.vo
 * @ClassName:Result
 * @Description:
 * @author:Aan
 * @data 2022/1/18 18:03
 **/
@Data
@AllArgsConstructor
public class Result {

    private boolean success;

    private  int code;

    private String msg;

    private  Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }
    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }


}
