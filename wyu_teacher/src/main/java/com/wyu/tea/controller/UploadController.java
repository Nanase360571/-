package com.wyu.tea.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.common.dao.pojo.classes;
import com.wyu.common.dao.pojo.classesCourse;
import com.wyu.common.dao.pojo.courseTeacherStudent;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.mapper.ClassesCourseMapper;
import com.wyu.tea.dao.mapper.ClassesMapper;
import com.wyu.tea.dao.mapper.CourseTeacherStudentMapper;
import com.wyu.tea.dao.mapper.StudentMapper;
import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.poiReadExcel.poiReadExcel;
import com.wyu.tea.service.AddStudent;
import com.wyu.tea.service.ClassesService;
import com.wyu.tea.service.Impl.ClassesServiceImpl;
import com.wyu.tea.util.MultipartFileToFile;
import com.wyu.tea.vo.params.StudentFromExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @PackageName:com.wyu.stu.controller
 * @ClassName:UploadController
 * @Description:
 * @author:Aan
 * @data 2022/1/25 14:10
 **/
@RestController
@RequestMapping
public class UploadController {
    @Autowired
    private AddStudent addStudent;

    @Autowired
    private ClassesService classesService;

    @Autowired
    private CourseTeacherStudentMapper courseTeacherStudentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private ClassesCourseMapper classesCourseMapper;

    @PostMapping("/upload")
    @Transactional
    public Result upload(@RequestParam Map<String, String> map, MultipartFile file) throws Exception {
        Integer teacherId = Integer.parseInt(map.get("teacherId"));
        Integer courseId = Integer.parseInt(map.get("courseId"));
        File file1 = MultipartFileToFile.multipartFileToFile(file);
        System.out.println(file.getOriginalFilename());
        // 文件保存路径
        String targetFilePath = "C:\\Users\\86158";

        // 获取上传文件的文件名
        String fileName = file.getOriginalFilename();

        // 真正地进行文件保存
        File targetFile = new File(targetFilePath + File.separator + fileName);
        String absolutePath = file1.getAbsolutePath();
        System.out.println(absolutePath+"{}");
        List<student> studentList = poiReadExcel.read(absolutePath);
        List<String> claNoList = studentList.stream().map(student::getClaNo).collect(Collectors.toList());
        List<String> stringList = claNoList;
        List<String> collect = claNoList.stream().distinct().collect(Collectors.toList());
        List<classes> classesList = new ArrayList<>();
        for (String s : collect) {
            classes classes = new classes();
            classes.setClaNo(s);
            classesList.add(classes);
        }

        /*
        * 将所有学生加入到student表中
        * */
        List<String> accountList = studentList.stream().map(student::getAccount).collect(Collectors.toList());
        QueryWrapper<student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.in("account",accountList);
        List<student> isExistList = studentMapper.selectList(studentQueryWrapper);
        //将已经存在student表中的学生去除，然后将不存在的加入到student表中
        List<student> studentList2 = studentList;
        List<String> tempAllStudent = studentList2.stream().map(student::getAccount).collect(Collectors.toList());
        List<String> tempIsExitStudent = isExistList.stream().map(student::getAccount).collect(Collectors.toList());
        List<Integer> tagList = new ArrayList<>();
        //将已经存在的student对应的all下标提取出来
        for (String s : tempIsExitStudent) {
            if(tempAllStudent.contains(s)){
                tagList.add(tempAllStudent.indexOf(s));
            }
        }
        if(tagList.size() != 0){
            List<student> list = new ArrayList<>();
            //将不存在student的提取出来
            for (int i = 0; i < studentList2.size(); i++) {
                if(tagList.contains(i))
                {
                    continue;
                }
                list.add(studentList2.get(i));
            }
            //添加
            if(list.size() != 0){addStudent.addPatch(list);}
        }
        else {
            //添加
            if(studentList2.size() != 0)
            {
                addStudent.addPatch(studentList2);
            }
        }



        /*
        * 将所有班级加入到classes表中
        * */
        //查询已经存在了的班级
        List<String> classNoList = classesList.stream().map(classes::getClaNo).collect(Collectors.toList());
        QueryWrapper<classes> classesQueryWrapper = new QueryWrapper<>();
        classesQueryWrapper.in("cla_no",classNoList);
        List<classes> existClassList = classesMapper.selectList(classesQueryWrapper);


        //将已经存在了的班级去除，将未加入班级表的班级加入班级表
        List<String> allClassesNoList = classesList.stream().map(classes::getClaNo).collect(Collectors.toList());
        List<String> isExistClassesNoList = existClassList.stream().map(classes::getClaNo).collect(Collectors.toList());
        List<Integer> tag2 = new ArrayList<>();
        for (int i = 0; i < isExistClassesNoList.size(); i++) {
            if(allClassesNoList.contains(isExistClassesNoList.get(i))){
                tag2.add(i);
            }
        }
        //将未加入classes表的班级去重汇总
        List<classes> classesList1 = new ArrayList<>();
        if(tag2.size() != 0)
        {
            for (int i = 0; i < classesList.size(); i++) {
                if(tag2.contains(i)){
                    continue;
                }
                classesList1.add(classesList.get(i));
            }
            if(classesList1.size() != 0){
                //添加
                classesService.addPatchClass(classesList1);

            }
        }else {
            //添加
            if(classesList.size() != 0)
            {
                classesService.addPatchClass(classesList);
            }
        }



        /*
        * 4将所有学生加入到该老师的课程中
        * */
        /*
        * 4.1 需要先把加入课程的学生ID查询出来，因为插入的时候，如果该同学已经存在，那么是不会返回他的ID的*/

        /*
        * 4.1.1先将之前已经存在student表的学生筛选出来*/
        List<String> existStudent = new ArrayList<>();
        List<Integer> arrayList = new ArrayList<>();

        for (student student : studentList) {
            if(student.getId() == null){
                existStudent.add(student.getAccount());
            }
            else {
                arrayList.add(student.getId());
            }
        }
        /*4.1.2查询existStudent这部分*/
        List<student> selectList = new ArrayList<>();
        if(existStudent.size() != 0){
            QueryWrapper<student> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("account",existStudent);
            selectList = studentMapper.selectList(queryWrapper);
        }

        /*4.1.3将两部分ID合并，插入到课程-教师-学生关系表中*/
        for (student student : selectList) {
            arrayList.add(student.getId());
        }

        List<courseTeacherStudent> courseTeacherStudents = new ArrayList<>();
        for (Integer integer : arrayList) {
            courseTeacherStudent c =new courseTeacherStudent();
            c.setCtsStudent(integer);
            c.setCtsCourse(courseId);
            c.setCtsTeacher(teacherId);
            courseTeacherStudents.add(c);
        }
        /*
        *查询所有学生是否已经加入该班级了，返回existCourseTeacherStudentList
        * */
        List<courseTeacherStudent> existCourseTeacherStudentList = courseTeacherStudentMapper.getPatchcourseTeacherStudents(courseTeacherStudents);
        //list是传入来加入班级的，需要去重
        List<courseTeacherStudent> list = new ArrayList<>();
        for (Integer integer : arrayList) {
            courseTeacherStudent courseTeacherStudent = new courseTeacherStudent();
            courseTeacherStudent.setCtsTeacher(teacherId);
            courseTeacherStudent.setCtsCourse(courseId);
            courseTeacherStudent.setCtsStudent(integer);
            list.add(courseTeacherStudent);
        }
        //将existCourseTeacherStudentList的所有ID设置为null
        if(existCourseTeacherStudentList.size() != 0){
            for (int i = 0; i < existCourseTeacherStudentList.size(); i++) {
                courseTeacherStudent courseTeacherStudent = existCourseTeacherStudentList.get(i);
                courseTeacherStudent.setId(null);
                existCourseTeacherStudentList.set(i,courseTeacherStudent);
            }

            List<Integer> tag3 = new ArrayList<>();
            for (int i = 0; i < existCourseTeacherStudentList.size(); i++) {
                if(list.contains(existCourseTeacherStudentList.get(i))){
                    //表示该学生已经存在班级，不需要加入了
                    continue;
                }
                tag3.add(i);
            }
            if(tag3.size() != 0){
                List<courseTeacherStudent> list1 = new ArrayList<>();
                for (Integer integer : tag3) {
                    //将去重后需要加入班级的学生从总的list中提取
                    list1.add(list.get(integer));
                }
                if ((list1.size() != 0))
                {
                    //证明已经有学生加入该班级了，去重后加入班级
                    courseTeacherStudentMapper.addPatchStudentToCourse(list1);
                }else {
                    courseTeacherStudentMapper.addPatchStudentToCourse(list);

                }
            }
        }
        else {
            courseTeacherStudentMapper.addPatchStudentToCourse(list);
        }

        /**
         *将班级Id，和courseId，classId插入到class-course表中
         * */

//        for (classes classes : existClassList) {
//            classesCourse classesCourse = new classesCourse();
//            classesCourse.setCcCourse(courseId);
//            classesCourse.setCcTeacher(teacherId);
//            classesCourse.setCcClass(classes.getId());
//            classesCourseMapper.insertClasses(classesCourse);
//        }
//        Integer a = 0;
//        System.out.println(a.equals(1));
        QueryWrapper<classes> classesQueryWrapper2 = new QueryWrapper<>();
        classesQueryWrapper.in("cla_no",classNoList);
        List<classes> existClassList2 = classesMapper.selectList(classesQueryWrapper);
        for (classes classes : existClassList2) {
            classesCourse classesCourse = new classesCourse();
            classesCourse.setCcCourse(courseId);
            classesCourse.setCcTeacher(teacherId);
            classesCourse.setCcClass(classes.getId());
            //classesCourseMapper.insertClasses(classesCourse);
            List<com.wyu.common.dao.pojo.classesCourse> list1 = classesCourseMapper.getList();
            classesCourseMapper.insert(classesCourse);


        }

//        for (String s : stringList) {
//            classesCourse classesCourse = new classesCourse();
//            classesCourse.setCcClass(Integer.parseInt(s));
//            classesCourse.setCcCourse(courseId);
//            classesCourse.setCcTeacher(teacherId);
//            classesCourseMapper.insertClasses(classesCourse);
//        }





        return Result.success(200);
    }
}
