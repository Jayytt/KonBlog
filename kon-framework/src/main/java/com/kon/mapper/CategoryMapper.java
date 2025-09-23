package com.kon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kon.domain.entity.Category;
import com.kon.domain.vo.ArticleListVo;

import java.util.List;

/**
 * @author 35238
 * @date 2023/7/19 0019 22:38
 */
public interface CategoryMapper extends BaseMapper<Category> {
    List<ArticleListVo> searchList(String search);
}