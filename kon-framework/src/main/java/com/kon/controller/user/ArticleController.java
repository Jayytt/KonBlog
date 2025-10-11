package com.kon.controller.user;

import com.kon.annotation.MySystemLog;
import com.kon.mapper.CategoryMapper;
import com.kon.result.ResponseResult;
import com.kon.service.IArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @MySystemLog(businessName = "热门文章列表")//自定义日志注解
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
    @Operation(summary = "分页查询文章列表")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        return articleService.articleList(pageNum, pageSize, categoryId);

}

    @GetMapping("{id}")
    @Operation(summary = "获取文章详情内容")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        log.info("获取到的文章id,{}",id);
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @Operation(summary = "根据文章id从mysql查询文章")
    @MySystemLog(businessName = "根据文章id从mysql查询文章")//接口描述，用于'日志记录'功能
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}