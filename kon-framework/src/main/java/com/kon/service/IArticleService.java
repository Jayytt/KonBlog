package com.kon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.entity.Article;
import com.kon.result.ResponseResult;


public interface IArticleService extends IService<Article> {
    /*查询热门文章列表*/
    ResponseResult hotArticleList();

    /*分页查询文章列表*/
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /*根据id查询文章详情*/
    ResponseResult getArticleDetail(Long id);
}
