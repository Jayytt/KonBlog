package com.kon.controller;

import com.kon.domain.entity.Article;

import com.kon.domain.entity.Category;
import com.kon.domain.vo.ArticleListVo;
import com.kon.domain.vo.PageVo;
import com.kon.mapper.ArticleMapper;
import com.kon.mapper.CategoryMapper;
import com.kon.result.ResponseResult;
import com.kon.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Slf4j
@Tag(name = "文章相关接口")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/hotArticleList")
    @Operation(summary = "热门文章列表")
    public ResponseResult hotArticleList() {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    /*   @GetMapping("/articleList")
       public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId, @RequestParam(required = false) String search) {
           if (search != "") {
               List<ArticleListVo> articleListVo = categoryMapper.searchList(search);
               if (articleListVo.size() != 0) {
                   PageVo pageVo = new PageVo(articleListVo, articleListVo.get(0).getTotal());
                   List<ArticleListVo> list = pageVo.getRows();
                   for (ArticleListVo one : list) {
                       Category category = categoryMapper.selectById(one.getCategoryId());
                       one.setCategoryName(category.getName());
                       one.setCategoryId(category.getId());
                   }
                   return ResponseResult.okResult(pageVo);
               }
           }
           ResponseResult responseResult = articleService.articleList(pageNum, pageSize, categoryId);
           return responseResult;*/

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);

}

    @GetMapping("{id}")
    @Operation(summary = "获取文章详情内容")
    public ResponseResult getArticleDetail(@PathVariable Long id) {
        return articleService.getArticleDetail(id);
    }
}