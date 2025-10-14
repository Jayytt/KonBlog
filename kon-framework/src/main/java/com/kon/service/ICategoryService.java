package com.kon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.entity.Category;
import com.kon.domain.vo.CategoryVo;
import com.kon.result.ResponseResult;

import java.util.List;

/**
 * 分类表(SgCategory)表服务接口
 *
 * @author makejava
 * @since 2025-09-23 09:35:27
 */
public interface ICategoryService extends IService<Category> {
//查询分类列表
    ResponseResult getCategoryList();

    //写博客-查询文章分类的接口
    List<CategoryVo> listAllCategory();
}