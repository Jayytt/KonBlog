package com.kon.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kon.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2025-09-23 09:42:05
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {


}

