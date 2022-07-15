package com.wyu.tea.util;

import com.wyu.tea.dao.pojo.teacher;

public class currentUser {
    private currentUser(){};
    public final static ThreadLocal<teacher> teacherThreadLocal = new ThreadLocal<>();
    public static  void put(teacher teacher) {teacherThreadLocal.set(teacher);}
    public static teacher get(){return teacherThreadLocal.get();}
    public static void remove(){teacherThreadLocal.remove();}


}
