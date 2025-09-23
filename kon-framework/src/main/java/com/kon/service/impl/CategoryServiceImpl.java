package com.kon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.constant.SystemConstants;
import com.kon.domain.entity.Article;
import com.kon.domain.entity.Category;
import com.kon.domain.vo.CategoryVO;
import com.kon.mapper.CategoryMapper;
import com.kon.result.ResponseResult;
import com.kon.service.IArticleService;
import com.kon.service.ICategoryService;
import com.kon.utils.BeanCopyUtils;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 分类表(SgCategory)表服务实现类
 *
 * @author makejava
 * @since 2025-09-23 09:35:27
 */
@AllArgsConstructor
@Service("categoryService")
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    private final IArticleService articleService;

    /*  @Override
      public ResponseResult getCategoryList() {
          //先查询文章表 状态为已发布的文章
          LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
          articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
          List<Article> articles = articleService.list(articleWrapper);

          //获取文章的分类id，并且去重
          Set<Long> categoryIds = articles.stream()
                  .map(article -> article.getCategoryId())
                  .collect(Collectors.toSet());

          //必须展示有正式文章发布的分类
          List<Category> categories = listByIds(categoryIds);
          categories.stream()
                  .filter(category ->
                          SystemConstants.CATEGORY_STATUS_NORMAL==(category.getStatus()))
                  .collect(Collectors.toList());
          //封装VO
          List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);


          return ResponseResult.okResult(categoryVOS);*/
    @Override
    public ResponseResult getCategoryList() {
        // 1. 查询所有“已发布”的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(articleWrapper);

        // 2. 提取文章关联的分类ID并去重
        Set<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        // 3. 查询“有已发布文章”且“自身状态正常”的分类（关键修复：数据库层面过滤）
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        // 条件1：分类ID在已提取的ID集合中（确保有已发布文章）
        categoryWrapper.in(Category::getId, categoryIds)
                // 条件2：分类状态为“正常”（直接在数据库过滤)
                .eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> categories = list(categoryWrapper); // 直接得到过滤后的结果

        // 4. 转换为VO并返回
        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);
        return ResponseResult.okResult(categoryVOS);
    }
}

