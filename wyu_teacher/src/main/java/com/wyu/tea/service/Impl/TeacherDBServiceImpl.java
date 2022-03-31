package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.common.dao.pojo.*;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.mapper.*;
import com.wyu.tea.service.TeacherDBService;
import com.wyu.tea.vo.DbTargetVo;
import com.wyu.tea.vo.params.UpdateKnowledgeAndTarget;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:TeacherDBServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/2/24 19:35
 **/
@Service
public class TeacherDBServiceImpl  implements TeacherDBService {

    @Autowired
    private DbCourseMapper dbCourseMapper;

    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private DbTargetTeacherMapper dbTargetTeacherMapper;
    @Autowired
    private TargetMapper targetMapper;
    @Autowired
    private CourseTargetTeacherMapper courseTargetTeacherMapper;
    @Autowired
    private CourseKnowledgeTeacherMapper courseKnowledgeTeacherMapper;
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private DbKnowledgeTeacherMapper dbKnowledgeTeacherMapper;
    @Autowired
    private CourseTeacherMajorMapper courseTeacherMajorMapper;
    @Autowired
    private MajorTargetCourseMapper majorTargetCourseMapper;

    @Override
    public Result getCourseDB(Integer courseId,Integer teacherId) {

        List<DbTargetVo> dbTargetVoList = new ArrayList<>();

        QueryWrapper<dbCourse> dbCourseQueryWrapper = new QueryWrapper<>();
        dbCourseQueryWrapper.eq("dc_course",courseId);
        List<dbCourse> dbCourseList = dbCourseMapper.selectList(dbCourseQueryWrapper);
        if(CollectionUtils.isEmpty(dbCourseList)){return null;}
        List<Integer> dbIdList = dbCourseList.stream().map(dbCourse::getDcDb).collect(Collectors.toList());

        /*
        * 获取到dbIdList，到题库中查询所有题目
        * */
        List<db> dbList = dbMapper.selectBatchIds(dbIdList);



        /*
         * 获取到dbIdList，到dbTeacherTarget表中查询所有目标
         * */
        List<Integer> collect = dbList.stream().map(db::getId).collect(Collectors.toList());
        QueryWrapper<dbTargetTeacher> dbTargetQueryWrapper = new QueryWrapper<>();
        dbTargetQueryWrapper.in("dt_db",collect);
        dbTargetQueryWrapper.eq("dt_teacher",teacherId);
        List<dbTargetTeacher> dbTargetTeacherList = dbTargetTeacherMapper.selectList(dbTargetQueryWrapper);
        List<Integer> collect1 = new ArrayList<>();
        List<target> targetArrayList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(dbTargetTeacherList)){
            collect1 = dbTargetTeacherList.stream().map(dbTargetTeacher::getDtTarget).collect(Collectors.toList());
            targetArrayList = targetMapper.selectBatchIds(collect1);
        }
        /*
         * 获取到dbIdList，到DbKnowledgeTeacher表中查询所有目标
         * */
        QueryWrapper<dbKnowledgeTeacher> dbKnowledgeTeacherQueryWrapper = new QueryWrapper<>();
        dbKnowledgeTeacherQueryWrapper.in("dkt_db",collect);
        dbKnowledgeTeacherQueryWrapper.eq("dkt_teacher",teacherId);
        List<dbKnowledgeTeacher> dbKnowledgeTeachers = dbKnowledgeTeacherMapper.selectList(dbKnowledgeTeacherQueryWrapper);
        List<knowledge> knowledgeArrayList = new ArrayList<>();
        List<Integer> collect2 = new ArrayList<>();
        if(!CollectionUtils.isEmpty(dbKnowledgeTeachers)){
            collect2 = dbKnowledgeTeachers.stream().map(dbKnowledgeTeacher::getDktKnowledge).collect(Collectors.toList());
            knowledgeArrayList = knowledgeMapper.selectBatchIds(collect2);
        }


        /*
        * 封装结果
        * */
            int tag1 = 0 ;
            int tag2 = 0 ;
            for (db db : dbList) {
                DbTargetVo dbTargetVo = new DbTargetVo();
                dbTargetVo.setDbId(db.getId());
                dbTargetVo.setDbType(getDbType(db.getDbType()));
                dbTargetVo.setDbAnswer((db.getDbAnswer()));
                dbTargetVo.setDbContent(db.getDbContent());
                if(!CollectionUtils.isEmpty(dbTargetTeacherList)){
                    if(tag1 < collect1.size()){
                        for (dbTargetTeacher dbTargetTeacher : dbTargetTeacherList) {
                            if(dbTargetVo.getDbId() == dbTargetTeacher.getDtDb())
                            {
                                dbTargetVo.setTargetId(dbTargetTeacher.getDtTarget());
                            }
                        }
                        Integer integer = collect1.get(tag1);
                        for (int i = 0; i < targetArrayList.size(); i++) {
                            {
                                if(targetArrayList.get(i).getId() == dbTargetVo.getTargetId())
                                {
                                    //dbTargetVo.setTargetId(targetArrayList.get(i).getId());
                                    dbTargetVo.setTarContent(targetArrayList.get(i).getTarContent());
                                    tag1++;
                                }
                            }
                        }
                    }

                }else {
                    dbTargetVo.setTargetId(-1);
                    dbTargetVo.setTarContent("未设置");
                }
                if(!CollectionUtils.isEmpty(dbKnowledgeTeachers)){
                    if(tag2 < collect2.size())
                    {
                        for (dbKnowledgeTeacher dbKnowledgeTeacher : dbKnowledgeTeachers) {
                            if(dbTargetVo.getDbId() == dbKnowledgeTeacher.getDktDb()){
                                dbTargetVo.setKnoId(dbKnowledgeTeacher.getDktKnowledge());
                            }
                        }
                        if(dbTargetVo.getKnoId()!=null)
                        {
                            Integer integer = collect2.get(tag2);
                            for (int i = 0; i < knowledgeArrayList.size(); i++) {
                                if (knowledgeArrayList.get(i).getId() == dbTargetVo.getKnoId()) {
//                                    dbTargetVo.setKnoId(knowledgeArrayList.get(i).getId());
                                    dbTargetVo.setKnoContent(knowledgeArrayList.get(i).getKnoContent());
                                    dbTargetVo.setKnoCourse(knowledgeArrayList.get(i).getKnoCourse());
                                    tag2++;
                                }
                            }
                        }

                    }
                }
                else {
                    dbTargetVo.setKnoId(-1);
                    dbTargetVo.setKnoContent("未设置");
                    dbTargetVo.setKnoCourse(-1);
                }
                dbTargetVoList.add(dbTargetVo);
            }
            return Result.success(dbTargetVoList);


    }

    @Override
    public Result getTargetList(Integer courseId, Integer teacherId) {


        List<Integer> collect1 = new ArrayList<>();
        List<target> targetArrayList = new ArrayList<>();


        QueryWrapper<courseTeacherMajor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ct_course",courseId)
                .eq("ct_teacher",teacherId);
        List<courseTeacherMajor> courseTeacherMajors = courseTeacherMajorMapper.selectList(queryWrapper);
        List<Integer> integerList = courseTeacherMajors.stream().map(courseTeacherMajor::getCtMajor).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(integerList)) {
            QueryWrapper<majorTargetCourse> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.in("mtc_major", integerList)
                    .eq("mtc_course", courseId);
            List<majorTargetCourse> list = majorTargetCourseMapper.selectList(queryWrapper1);
            if (!CollectionUtils.isEmpty(list)) {
                collect1 = list.stream().map(majorTargetCourse::getMtcTarget).collect(Collectors.toList());
                targetArrayList = targetMapper.selectBatchIds(collect1);
            }
        }

        return Result.success(targetArrayList);
    }

    @Override
    public Result getKnowledgeList(Integer courseId, Integer teacherId) {


        QueryWrapper<courseKnowledgeTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ck_course",courseId)
                .eq("ck_teacher",teacherId);
        List<courseKnowledgeTeacher> courseKnowledgeTeacherList = courseKnowledgeTeacherMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(courseKnowledgeTeacherList))
        {return Result.success(200);}
        List<Integer> collect = courseKnowledgeTeacherList.stream().map(courseKnowledgeTeacher::getCkKnowledge).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect))
        {return Result.success(200);}
        List<knowledge> knowledgeList = knowledgeMapper.selectBatchIds(collect);

        return Result.success(knowledgeList);
    }

    @Override
    public Result updateKnowledgeAndTarget(UpdateKnowledgeAndTarget updateKnowledgeAndTarget) {
        if(updateKnowledgeAndTarget.getKnoId()>0)
        {
            QueryWrapper<dbKnowledgeTeacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dkt_db",updateKnowledgeAndTarget.getDbId())
                    .eq("dkt_teacher",updateKnowledgeAndTarget.getTeacherId());
            int delete = dbKnowledgeTeacherMapper.delete(queryWrapper);

            dbKnowledgeTeacher dbKnowledgeTeacher = new dbKnowledgeTeacher();
            dbKnowledgeTeacher.setDktKnowledge(updateKnowledgeAndTarget.getKnoId());
            dbKnowledgeTeacher.setDktDb(updateKnowledgeAndTarget.getDbId());
            dbKnowledgeTeacher.setDktTeacher(updateKnowledgeAndTarget.getTeacherId());
            int insert = dbKnowledgeTeacherMapper.insert(dbKnowledgeTeacher);

        }
        if(updateKnowledgeAndTarget.getTargetId()>0){
            QueryWrapper<dbTargetTeacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dt_db",updateKnowledgeAndTarget.getDbId())
                    .eq("dt_teacher",updateKnowledgeAndTarget.getTeacherId());
            int delete = dbTargetTeacherMapper.delete(queryWrapper);

                dbTargetTeacher dbTargetTeacher = new dbTargetTeacher();
                dbTargetTeacher.setDtTarget(updateKnowledgeAndTarget.getTargetId());
                dbTargetTeacher.setDtTeacher(updateKnowledgeAndTarget.getTeacherId());
                dbTargetTeacher.setDtDb(updateKnowledgeAndTarget.getDbId());
                int insert = dbTargetTeacherMapper.insert(dbTargetTeacher);

        }
        return Result.success(200);
    }


    public static String getDbType(Integer type)
    {
        switch (type){
            case 1:return "单选题";
            case 2:return "多选题";
            case 3:return "判断题";
            default:return "错误";
        }
    }
}
