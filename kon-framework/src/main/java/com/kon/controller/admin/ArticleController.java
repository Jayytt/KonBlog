package com.kon.controller.admin;

import com.kon.domain.dto.AddArticleDTO;
import com.kon.result.ResponseResult;
import com.kon.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 35238
 * @date 2023/8/7 0007 15:21
 */
@RestController(value = "adminArticleController")
@RequestMapping("/content/article")
@Tag(name = "后台博客文章相关接口")
public class ArticleController {

    //------------------------------新增博客文章-----------------------------

    @Autowired
    private IArticleService articleService;

    @PostMapping
    @Operation(summary = "新增博客文章")
    public ResponseResult add(@RequestBody AddArticleDTO article){
        return articleService.add(article);
    }
}