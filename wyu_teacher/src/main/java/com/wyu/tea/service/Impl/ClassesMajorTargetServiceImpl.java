package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.jmx.remote.internal.ArrayQueue;
import com.wyu.common.dao.pojo.*;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.mapper.*;
import com.wyu.tea.service.ClassesMajorTargetService;
import com.wyu.tea.vo.TargetVo;
import com.wyu.tea.vo.params.RemoveAndAddMajorClassesParam;
import com.wyu.tea.vo.params.SetKnowledgeForCourse;
import com.wyu.tea.vo.params.SetTargetForMajorParam;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:ClassesMajorTargetService
 * @Description:
 * @author:Aan
 * @data 2022/2/15 18:25
 **/
@Service
public class ClassesMajorTargetServiceImpl implements ClassesMajorTargetService {
    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private MajorClassesMapper majorClassesMapper;

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private CourseTeacherStudentMapper courseTeacherStudentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MajorTargetCourseMapper majorTargetCourseMapper;

    @Autowired
    private TargetMapper targetMapper;

    @Autowired
    private CourseKnowledgeTeacherMapper courseKnowledgeTeacherMapper;

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Autowired
    private CourseTargetTeacherMapper courseTargetTeacherMapper;

    @Autowired
    private CourseTeacherMajorMapper courseTeacherMajorMapper;

    @Override
    public Result getMajorList() {
        List<major> majors = majorMapper.selectList(null);
        return Result.success(majors);
    }

    @Override
    public Result getMajorClasses(Integer majorId) {
        QueryWrapper<majorClasses> majorClassesQueryWrapper = new QueryWrapper<>();
        majorClassesQueryWrapper.eq("mc_major",majorId);
        List<majorClasses> majorClassesList = majorClassesMapper.selectList(majorClassesQueryWrapper);

        if(CollectionUtils.isEmpty(majorClassesList)){
            return Result.success(null);
        }

        List<Integer> integerList = majorClassesList.stream().map(majorClasses::getMcClasses).collect(Collectors.toList());
        QueryWrapper<classes> classesQueryWrapper =new QueryWrapper<>();
        classesQueryWrapper.in("id",integerList);
        List<classes> classesList = classesMapper.selectList(classesQueryWrapper);

        return Result.success(classesList);
    }

    @Override
    public Result removeMajorClasses(RemoveAndAddMajorClassesParam param) {
        QueryWrapper<majorClasses> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mc_major",param.getMajorId())
                .eq("mc_classes",param.getClassesId());
        int delete = majorClassesMapper.delete(queryWrapper);
        return Result.success(delete == 1  ? 200 : 10005);
    }

    @Override
    public Result addClassesToMajor(RemoveAndAddMajorClassesParam param) {
        QueryWrapper<majorClasses> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mc_major",param.getMajorId())
                .eq("mc_classes",param.getClassesId());
        majorClasses majorClasses = new majorClasses();
        majorClasses.setMcClasses(param.getClassesId());
        majorClasses.setMcMajor(param.getMajorId());
        int insert = majorClassesMapper.insert(majorClasses);
        return Result.success(insert == 1 ?200:10005);
    }

    @Override
    public Result getLeftClasses(Integer majorId) {
        QueryWrapper<majorClasses> majorClassesQueryWrapper = new QueryWrapper<>();
        majorClassesQueryWrapper.eq("mc_major",majorId);
        List<majorClasses> majorClassesList = majorClassesMapper.selectList(majorClassesQueryWrapper);

        if(CollectionUtils.isEmpty(majorClassesList)){
            List<classes> classes = classesMapper.selectList(null);
            return Result.success(classes);
        }else {
            List<Integer> integerList = majorClassesList.stream().map(majorClasses::getMcClasses).collect(Collectors.toList());
            QueryWrapper<classes> classesQueryWrapper =new QueryWrapper<>();
            classesQueryWrapper.notIn("id",integerList);
            List<classes> classesList = classesMapper.selectList(classesQueryWrapper);

            return Result.success(classesList);
        }


    }

    /*
    * 查询该教师所有的课程
    * */
    @Override
    public Result getCourseList(Integer teacherId,Integer majorId) {
        //List<courseTeacherStudent> list=courseTeacherStudentMapper.getCourseList(teacherId);
        QueryWrapper<courseTeacherMajor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ct_teacher",teacherId);
        queryWrapper.eq("ct_major",majorId);
        List<courseTeacherMajor> courseTeacherMajors = courseTeacherMajorMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(courseTeacherMajors)){
            return Result.success(null);
        }else {
            List<Integer> courseIdList = courseTeacherMajors.stream().map(courseTeacherMajor::getCtCourse).collect(Collectors.toList());
            List<course> courseList = courseMapper.selectBatchIds(courseIdList);
            return Result.success(courseList);
        }
    }

    @Override
    public Result getTargetList(RemoveAndAddMajorClassesParam param) {
        QueryWrapper<majorTargetCourse> majorTargetCourseQueryWrapper = new QueryWrapper<>();
        majorTargetCourseQueryWrapper.eq("mtc_major",param.getMajorId())
                .eq("mtc_course",param.getCourseId());
        List<majorTargetCourse> majorTargetCoursesList = majorTargetCourseMapper.selectList(majorTargetCourseQueryWrapper);

        if(CollectionUtils.isEmpty(majorTargetCoursesList)){
            return Result.success(null);
        }
        List<Integer> integerList = majorTargetCoursesList.stream().map(majorTargetCourse::getMtcTarget).collect(Collectors.toList());
        List<target> targetList = targetMapper.selectBatchIds(integerList);
        List<String> stringList = targetList.stream().map(target::getTarContent).collect(Collectors.toList());

        List<TargetVo> targetVoList = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            TargetVo targetVo = new TargetVo();
            targetVo.setTarId(majorTargetCoursesList.get(i).getMtcTarget());
            targetVo.setTarContent(stringList.get(i));
            targetVo.setProportion(majorTargetCoursesList.get(i).getProportion());
            targetVoList.add(targetVo);
        }


        return Result.success(targetVoList);
    }

    /*
    * 将修改后的课程目标插入到数据库
    * */
    @Override
    @Transactional

    public Result setTargetForMajor(SetTargetForMajorParam param) {
        long startTime=System.currentTimeMillis();//开始时间
        /*
        * 前端传来数据可能包含以下情况
        *   1.课程目标被删除了
        *   2.课程目标内容或者占比被修改了
        *   3.新添了新的课程目标
        * 三种情况排列组合出现
        * 步骤：
        *   1.将前端传来的数据做划分，如果数据包含了tarId（目标Id），即是本身就存在该课程的，分一类，tarId为null的分一类，为新添加的
        *   2.将获取到的majorId和courseId到majorTargetCourse关系表中查询出所有的TargetId，然后到Target表中查询出所有的课程目标
        *   3.将Target表中查询出来的数据跟分类好的已存在了的课程目标做对比，如果Target表中的数据有多余，证明是被删除了，需要在majorTargetCourse关系表中删除关系
        *       如果数据有不一样的，即是Target内容发生变化，需要到Target表中更改
        *   4.将新添加的课程目标新增到Target表中
        *   5.将新添加的Target添加到majorTargetCourse关系表中，将被修改了占比的Target在majorTargetCourse关系表修改对应占比
        * */

        /*
        * 将获取到的targetList分为两种，一种是已存在target表中，tar_content可能修改了或者没修改,使用replace into插入
        * 另一种是新添加的，使用insert into插入
        * */
        List<TargetVo> targetVoList = param.getTargetList();
        List<target> existTargetList = new ArrayList<>();
        List<target> noExistTargetList = new ArrayList<>();
        List<Integer> deleteIdList = new ArrayList<>();//用于记录被删除的下标
        List<Integer> noExistList = new ArrayList<>();//用于记录新添加的记录下表在总记录的位置

        /*
        * 因为前端传来的数据中，如果是新添加的，那么它的tarId是null值
        * 在调用getId方法时候会包空指针错误，这时候捕捉他，然后将其添加到noExistTargetList中
        * */

        for (int i = 0; i < targetVoList.size(); i++) {
            target target= new target();
            try{
                if(targetVoList.get(i).getTarId().equals(null)){
                    target.setTarContent(targetVoList.get(i).getTarContent());
                    noExistTargetList.add(target);
                    noExistList.add(i);
                }else {
                    target.setTarContent(targetVoList.get(i).getTarContent());
                    target.setId(targetVoList.get(i).getTarId());

                    existTargetList.add(target);
                }
            }
            catch (NullPointerException exception){
                target.setTarContent(targetVoList.get(i).getTarContent());
                noExistTargetList.add(target);
                noExistList.add(i);
            }
        }

        /*
        *   1.到majorTargetCourse关系表查询targetID
        *   2.到数据库查询所有TargetList
        * */
        QueryWrapper<majorTargetCourse> queryWrapper =new QueryWrapper<>();
        queryWrapper.in("mtc_major",param.getMajorId())
                .eq("mtc_course",param.getCourseId());
        List<majorTargetCourse> majorTargetCourses = majorTargetCourseMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(majorTargetCourses)){
            List<Integer> collect = majorTargetCourses.stream().map(majorTargetCourse::getMtcTarget).collect(Collectors.toList());
            List<target> targets = targetMapper.selectBatchIds(collect);
            if(!CollectionUtils.isEmpty(targets)){
                /*
                * 查询是否有target被删除了
                * */
                List<target> deleteList = new ArrayList<>();
                List<Integer> collect2 = targets.stream().map(target::getId).collect(Collectors.toList());
                List<Integer> collect3 = existTargetList.stream().map(target::getId).collect(Collectors.toList());
                for (int i = 0; i < collect2.size(); i++) {
                    if(collect3.contains(collect2.get(i))){
                        continue;
                    }
                    deleteList.add(targets.get(i));
                    deleteIdList.add(i);
                }
                if(!CollectionUtils.isEmpty(deleteList)){
                    /*
                    * 将被删除了的target，从majorTargetCourse表中删除
                    * */
                    List<Integer> collect1 = deleteList.stream().map(target::getId).collect(Collectors.toList());
                    QueryWrapper<majorTargetCourse> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.in("mtc_target",collect1);
                    int delete = majorTargetCourseMapper.delete(queryWrapper1);
                    QueryWrapper<courseTargetTeacher> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.in("ctt_target",collect1);
                    int delete1 = courseTargetTeacherMapper.delete(queryWrapper2);
                    /*
                    *将被删除了的target，从target表中删除
                    * */
                    int i = targetMapper.deleteBatchIds(collect1);
                    /*
                    * 将被删除的target移除
                    * */
                    existTargetList.removeAll(deleteList);
                }
            }

            /*
            * 删除问题解决，existTargetList可以使用
            * */
            /*
            * 解决更改内容问题
            * */
            if(!CollectionUtils.isEmpty(targets)){
                /*
                * 查询被修改了的
                * */
                List<String> collect4 = existTargetList.stream().map(target::getTarContent).collect(Collectors.toList());
                List<String> collect5 = targets.stream().map(target::getTarContent).collect(Collectors.toList());
                List<target> targetList = new ArrayList<>();
                for (int i = 0; i < collect5.size(); i++) {
                    if(collect4.contains(collect5.get(i)))
                    {continue;}
                    targetList.add(targets.get(i));
//                    targetList.add(existTargetList.get(i));
                }

                if(!CollectionUtils.isEmpty(targetList)){
                    /*
                    * 将被修改的更新到数据库，该方法效率低，建议使用xml文件进行sql拼接
                    * */
                    for (target target : targetList) {
                        targetMapper.updateById(target);
                    }
                }

            }
            /*
            * 解决更改proportion问题
            * */
            if(!CollectionUtils.isEmpty(targets)) {
                /*
                * deleteIdList.将被删除了的移除出list
                * */
                if (!CollectionUtils.isEmpty(deleteIdList)){
                    for (Integer integer : deleteIdList) {
                        targetVoList.remove(integer);

                    }
                }
                if(!CollectionUtils.isEmpty(targetVoList))
                {
                    List<majorTargetCourse> list = new ArrayList<>();

                    for (TargetVo targetVo : targetVoList) {
                        majorTargetCourse majorTargetCourse = new majorTargetCourse();
                        majorTargetCourse.setMtcMajor(param.getMajorId());
                        majorTargetCourse.setMtcCourse(param.getCourseId());
                        majorTargetCourse.setMtcTarget(targetVo.getTarId());
                        majorTargetCourse.setProportion(targetVo.getProportion());
                        list.add(majorTargetCourse);
                    }
                    /*
                    * 将修改proportion的更新到表中
                    * */
                    majorTargetCourseMapper.insertBatchExist(list);
                }
            }
            /*
            * 解决插入新的target问题
            * */
            if(!CollectionUtils.isEmpty(noExistTargetList))
            {
                //返回ID
                targetMapper.insertBatchNotExist(noExistTargetList);
            }
            List<majorTargetCourse> list = new ArrayList<>();
            List<courseTargetTeacher> courseTargetTeacherList = new ArrayList<>();
            int tag = 0;
            for (target target : noExistTargetList) {
                courseTargetTeacher  courseTargetTeacher = new courseTargetTeacher();
                majorTargetCourse mtc = new majorTargetCourse();
                mtc.setMtcMajor(param.getMajorId());
                mtc.setMtcTarget(target.getId());
                mtc.setMtcCourse(param.getCourseId());
                mtc.setProportion(targetVoList.get(noExistList.get(tag)).getProportion());
                list.add(mtc);

                courseTargetTeacher.setCttTarget(target.getId());
                courseTargetTeacher.setCttTeacher(param.getTeacherId());
                courseTargetTeacher.setCttCourse(param.getCourseId());
                courseTargetTeacherMapper.insert(courseTargetTeacher);
                tag++;
            }
            if(!CollectionUtils.isEmpty(list))
            {
                majorTargetCourseMapper.insertBatchNoExist(list);
            }
        }
        else {
            for (TargetVo targetVo : targetVoList) {
                if("".equals(targetVo.getTarContent())){
                    continue;
                }
                courseTargetTeacher courseTargetTeacher = new courseTargetTeacher();
                target target = new target();
                target.setTarContent(targetVo.getTarContent());
                targetMapper.insert(target);
                majorTargetCourse mtc = new majorTargetCourse();
                mtc.setMtcMajor(param.getMajorId());
                mtc.setMtcTarget(target.getId());
                mtc.setMtcCourse(param.getCourseId());
                mtc.setProportion(targetVo.getProportion());
                majorTargetCourseMapper.insert(mtc);

                courseTargetTeacher.setCttTarget(target.getId());
                courseTargetTeacher.setCttTeacher(param.getTeacherId());
                courseTargetTeacher.setCttCourse(param.getCourseId());
                courseTargetTeacherMapper.insert(courseTargetTeacher);
            }
        }
//
//        if(!CollectionUtils.isEmpty(existTargetList)){
//            targetMapper.insertBatch(existTargetList);
//        }
//        if(!CollectionUtils.isEmpty(noExistTargetList)){
//            targetMapper.insertBatchNotExist(noExistTargetList);
//        }
//
//        /*
//        * 将课程目标更新到target表后，在课程-专业-目标表中更新数据
//        * */
//        List<majorTargetCourse> existMajorTargetCoursesList = new ArrayList<>();
//        List<majorTargetCourse> noExistMajorTargetCoursesList = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(existTargetList)){
//            for (int i = 0; i < existTargetList.size(); i++) {
//                majorTargetCourse m =new majorTargetCourse();
//                m.setMtcCourse(param.getCourseId());
//                m.setMtcTarget(existTargetList.get(i).getId());
//                m.setProportion(targetVoList.get(i).getProportion());
//                m.setMtcMajor(param.getMajorId());
//                existMajorTargetCoursesList.add(m);
//            }
//        }
//        if(!CollectionUtils.isEmpty(noExistTargetList)){
//            for (int i = 0; i < noExistTargetList.size(); i++) {
//                majorTargetCourse m =new majorTargetCourse();
//                m.setMtcCourse(param.getCourseId());
//                m.setMtcTarget(noExistTargetList.get(i).getId());
//                m.setProportion(targetVoList.get(i+existTargetList.size()-1).getProportion());
//                m.setMtcMajor(param.getMajorId());
//                noExistMajorTargetCoursesList.add(m);
//
//            }
//        }
//        if(!Collections.isEmpty(existMajorTargetCoursesList))
//        {
//            majorTargetCourseMapper.insertBatchExist(existMajorTargetCoursesList);
//
//        }
//        if(!Collections.isEmpty(noExistMajorTargetCoursesList))
//        {
//            majorTargetCourseMapper.insertBatchNoExist(noExistMajorTargetCoursesList);
//
//        }
        long endTime=System.currentTimeMillis();//结束时间

        float excTime=(float)(endTime-startTime)/1000;

        System.out.println("执行时间为："+excTime+"s");
        return Result.success(200);
    }

    @Override
    public Result getKnowledgeList(Integer courseId, Integer teacherId) {
        QueryWrapper<courseKnowledgeTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ck_course",courseId)
                .eq("ck_teacher",teacherId);
        List<courseKnowledgeTeacher> courseKnowledgeTeachers = courseKnowledgeTeacherMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(courseKnowledgeTeachers))
        {return null;}
        List<Integer> collect = courseKnowledgeTeachers.stream().map(courseKnowledgeTeacher::getCkKnowledge).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect))
        {
            return null;
        }
        List<knowledge> knowledgeList = knowledgeMapper.selectBatchIds(collect);

        return Result.success(knowledgeList);
    }

    @Override
    public Result setKnowledgeForCourse(SetKnowledgeForCourse param) {
        /*
        * 将修改后的知识点存入数据库
        * */
        Result knowledgeList = this.getKnowledgeList(param.getCourseId(), param.getTeacherId());
        List<knowledge> requestTargetList = param.getKnowledgeList();

        if(knowledgeList != null){
            List<knowledge> searchTargetList = (List)knowledgeList.getData();

            /*
             * 处理被删除了的知识点
             * */
            List<Integer> collect1 = searchTargetList.stream().map(knowledge::getId).collect(Collectors.toList());
            List<Integer> collect2 = requestTargetList.stream().map(knowledge::getId).collect(Collectors.toList());
            List<Integer> deleteList = new ArrayList<>();
            for (Integer integer : collect1) {
                if(collect2.contains(integer))
                {
                    continue;
                }
                deleteList.add(integer);
            }
            if(!CollectionUtils.isEmpty(deleteList))
            {
                QueryWrapper<courseKnowledgeTeacher> queryWrapper =new QueryWrapper<>();
                queryWrapper.eq("ck_teacher",param.getTeacherId())
                        .in("ck_knowledge",deleteList);
                courseKnowledgeTeacherMapper.delete(queryWrapper);
            }


            /*
             * 处理修改过内容的knowledge
             * */
            List<knowledge> updateKnowledgeList = new ArrayList<>();
            for (knowledge knowledge : searchTargetList) {
                for (com.wyu.common.dao.pojo.knowledge knowledge1 : requestTargetList) {
                    if(knowledge1.getId()!=null && knowledge.getId() == knowledge1.getId())
                    {
                        if(!knowledge.getKnoContent().equals(knowledge1.getKnoContent()) )
                        {
                            updateKnowledgeList.add(knowledge1);
                        }
                    }


                }
            }
            if(!CollectionUtils.isEmpty(updateKnowledgeList))
            {
                for (knowledge knowledge : updateKnowledgeList) {
                    knowledgeMapper.updateById(knowledge);
                }

            }
        }

        /*
         * 处理新增的知识点
         * */
        List<knowledge> newKnowledgeList = new ArrayList<>();
        for (knowledge knowledge : requestTargetList) {
            if(knowledge.getId() == null || knowledge.getId() == 0)
            {
                knowledge.setKnoCourse(param.getCourseId());
                newKnowledgeList.add(knowledge);
            }
        }
        if(!CollectionUtils.isEmpty(newKnowledgeList))
        {
            knowledgeMapper.insertBatchKnowledge(newKnowledgeList);
            for (knowledge knowledge : newKnowledgeList) {
                courseKnowledgeTeacher courseKnowledgeTeacher =new courseKnowledgeTeacher();
                courseKnowledgeTeacher.setCkCourse(param.getCourseId());
                courseKnowledgeTeacher.setCkKnowledge(knowledge.getId());
                courseKnowledgeTeacher.setCkTeacher(param.getTeacherId());
                courseKnowledgeTeacherMapper.insert(courseKnowledgeTeacher);
            }

        }
        return Result.success(200);
    }
}
