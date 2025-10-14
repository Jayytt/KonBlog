package com.kon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.dto.TagListDto;
import com.kon.domain.entity.Tag;
import com.kon.domain.vo.PageVo;
import com.kon.result.ResponseResult;


public interface TagService extends IService<Tag> {
    //查询标签列表
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);


}