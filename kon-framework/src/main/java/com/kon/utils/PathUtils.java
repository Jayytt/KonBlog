package com.kon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 35238
 * @date 2023/7/29 0029 12:45
 */
//对原始文件名进行修改文件名，并修改存放目录
public class PathUtils {

    public static String generateFilePath(String fileName){
        //根据日期生成路径   2025/9/28/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        /*避免文件重名冲突：不同用户上传的文件可能文件名相同，使用 UUID 可以保证每个文件名唯一，防止覆盖*/
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        return datePath + uuid + fileType;
    }
}