package com.kon.service;

import com.kon.result.ResponseResult;
import org.springframework.web.multipart.MultipartFile;


public interface OssUploadService {
    //图片上传到七牛云
    ResponseResult uploadImg(MultipartFile img);
}