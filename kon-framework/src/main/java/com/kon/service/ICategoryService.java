package com.kon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.entity.Category;
import com.kon.result.ResponseResult;

/**
 * 分类表(SgCategory)表服务接口
 *
 * @author makejava
 * @since 2025-09-23 09:35:27
 */
public interface ICategoryService extends IService<Category> {
/*查询分类列表*/
    ResponseResult getCategoryList();
}