package com.kon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.constant.SystemConstants;
import com.kon.domain.entity.Article;
import com.kon.domain.entity.Category;
import com.kon.domain.vo.ArticleDetailVo;
import com.kon.domain.vo.ArticleListVo;
import com.kon.domain.vo.HotArticleVo;
import com.kon.domain.vo.PageVo;
import com.kon.mapper.ArticleMapper;
import com.kon.result.ResponseResult;
import com.kon.service.IArticleService;
import com.kon.service.ICategoryService;
import com.kon.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    private final ICategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(SystemConstants.ARTICLE_STATUS_CURRENT, SystemConstants.ARTICLE_STATUS_SIZE);
        page(page, queryWrapper);

        //获取最终结果，封装在Article实体类里面
        List<Article> articles = page.getRecords();
        //Bean拷贝
        List<HotArticleVo> articleVOS = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
       /* for (Article article : articles) {
            HotArticleVo articleVO = new HotArticleVo();
            BeanUtils.copyProperties(article, articleVO);
            articleVOS.add(articleVO);
        }*/
        return ResponseResult.okResult(articleVOS);
    }


    //分页查询文章列表
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果前端传了 categoryId这个参数，那么查询的时候要和传入的相同。第二个参数是数据表的文章id，第三个字段是前端传来的文章id
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,
                Article::getCategoryId, categoryId);
        //状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        //对isTop字段进行降序排序，实现置顶的文章(isTop值为1)在最前面
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        /*
         *//**
         * 解决categoryName字段没有返回值的问题。在分页之后，封装成ArticleListVo之前，进行处理
         *//*
        //用categoryId来查询categoryName(category表的name字段)，也就是查询'分类名称'。有两种方式来实现，如下
        List<Article> articles = page.getRecords();
        //第一种方式，用for循环遍历的方式
        for (Article article : articles) {
            //'article.getCategoryId()'表示从article表获取category_id字段，然后作为查询category表的name字段
            Category category = categoryService.getById(article.getCategoryId());
            //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
            article.setCategoryName(category.getName());
        }*/

        /**
         * 解决categoryName字段没有返回值的问题。在分页之后，封装成ArticleListVo之前，进行处理。第二种方式，用stream流的方式
         */
        //用categoryId来查询categoryName(category表的name字段)，也就是查询'分类名称'
        List<Article> articles = page.getRecords();
        articles.stream()
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        //'article.getCategoryId()'表示从article表获取category_id字段，然后作为查询category表的name字段
                        Category category = categoryService.getById(article.getCategoryId());
                        String name = category.getName();
                        //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
                        article.setCategoryName(name);
                        //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
                        return article;
                    }
                })
                .collect(Collectors.toList());


        //把最后的查询结果封装成ArticleListVo(我们写的实体类)。BeanCopyUtils是我们写的工具类
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        //把上面那行查询结果和文章总数封装在PageVo中
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);

        //封装
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名
        Long categoryId = article.getCategoryId();
        Category category = categoryService.getById(categoryId);
        //如果根据分类id查询到的分类名，那么就把查询到的值付给ArticelDetailVo的categoryName字段
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }


}
