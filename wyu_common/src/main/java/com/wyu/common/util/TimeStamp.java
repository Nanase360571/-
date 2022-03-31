package com.wyu.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PackageName:com.OE.Anan.util
 * @ClassName:DateFormat
 * @Description:
 * @author:Aan
 * @data 2021/12/28 17:13
 **/
public class TimeStamp {

    public static Long getSimpleLongTime(String d)  {
        //Date或者String转化为时间戳
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        String time = d;
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            System.out.println("时间转换失败"+e.getMessage());
        }
        long time1 = date.getTime();
        System.out.print("Format To times:"+ time1);
        return time1;
    }

    public static String getSimpleStringTime(Long t){
        //时间戳转化为Sting或Date
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        Long time=new Long(t);
        String d = format.format(time);
        Date date= null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            System.out.println("时间转换失败"+e.getMessage());
        }
        System.out.println("Format To String(Date):"+d);
        return d;
    }

    public static Long getCurrentSimpleLongTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        System.out.println("=========="+date);
        String format = formatter.format(date);
        return getSimpleLongTime(format);
    }

    public static void main(String[] args) {
        Long currentSimpleLongTime = TimeStamp.getCurrentSimpleLongTime();
        System.out.println(currentSimpleLongTime);
        System.out.println(TimeStamp.getSimpleStringTime(currentSimpleLongTime));
    }
}
