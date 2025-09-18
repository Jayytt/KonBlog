package com.kon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kon.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ArticleMapper{

    @Select("SELECT * FROM sg_article")
    List<Article> selectList();
}
