package com.kon.controller;

import com.kon.result.ResponseResult;
import com.kon.service.OssUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 35238
 * @date 2023/7/29 0029 11:17
 */
@RestController
@Tag(name = "图片上传相关接口")
public class UploadController {

    @Autowired
    //UploadService是我们在huanf-framework写的接口
    private OssUploadService ossUploadService;

    @PostMapping("/upload")
    @Operation(summary = "图片上传")
    //MultipartFile是spring提供的接口，ResponseResult是我们在huanf-framework写的实体类
    public ResponseResult uploadImg(MultipartFile img){
        //图片上传到七牛云
        return ossUploadService.uploadImg(img);
    }
}