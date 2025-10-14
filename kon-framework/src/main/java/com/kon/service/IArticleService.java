package com.kon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.dto.AddArticleDTO;
import com.kon.domain.entity.Article;
import com.kon.result.ResponseResult;

/**
 * @author 35238
 * @date 2023/7/18 0018 21:16
 */
public interface IArticleService extends IService<Article> {

    //文章列表
    ResponseResult hotArticleList();

    //分类查询文章列表
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    //根据id查询文章详情
    ResponseResult getArticleDetail(Long id);

    //根据文章id从mysql查询文章
    ResponseResult updateViewCount(Long id);

    //新增博客文章
    ResponseResult add(AddArticleDTO article);
}