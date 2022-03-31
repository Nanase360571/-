package com.wyu.tea.vo.params;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:UploadVo
 * @Description:
 * @author:Aan
 * @data 2022/2/13 14:49
 **/
@Data
public class UploadVo {
    private MultipartFile file;
    private Integer teacherId;
    private Integer courseId;
}
