package com.wyu.tea.service.Impl;

import com.wyu.common.dao.pojo.classes;
import com.wyu.tea.dao.mapper.ClassesMapper;
import com.wyu.tea.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:ClassesServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/2/10 14:46
 **/
@Service
public class ClassesServiceImpl  implements ClassesService {
    @Autowired
    private ClassesMapper classesMapper;
    @Override
    public void addPatchClass(List<classes> claNoList) {
        classesMapper.addPatchClass(claNoList);
    }
}
