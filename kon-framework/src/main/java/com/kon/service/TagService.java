package com.kon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.dto.TagListDTO;
import com.kon.domain.entity.Tag;
import com.kon.domain.vo.PageVo;
import com.kon.domain.vo.TagVo;
import com.kon.result.ResponseResult;

import java.util.List;


public interface TagService extends IService<Tag> {
    //查询标签列表
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDTO tagListDto);

    //写博文-查询文章标签的接口
    List<TagVo> listAllTag();
}