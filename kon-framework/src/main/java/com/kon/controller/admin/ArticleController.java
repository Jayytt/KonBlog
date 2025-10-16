package com.kon.controller.admin;

import com.kon.domain.dto.AddArticleDTO;
import com.kon.domain.dto.ArticleDTO;
import com.kon.domain.entity.Article;
import com.kon.domain.entity.Category;
import com.kon.domain.vo.ArticleByIdVo;
import com.kon.domain.vo.PageVo;
import com.kon.result.ResponseResult;
import com.kon.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("@ps.hasPermission('content:article:writer')")//权限控制
    @Operation(summary = "新增博客文章")
    public ResponseResult add(@RequestBody AddArticleDTO article){
        return articleService.add(article);
    }

    //-----------------------------分页查询博客文章---------------------------

    @GetMapping("/list")
    @Operation(summary = "分页查询博客文章")
    public ResponseResult list(Article article, Integer pageNum, Integer pageSize){
        PageVo pageVo = articleService.selectArticlePage(article,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    //删除文章
    @DeleteMapping
    @Operation(summary = "删除博客文章")
    public ResponseResult remove(@RequestParam("ids") List<Long> ids) {
        articleService.removeByIds(ids);
        return ResponseResult.okResult();
    }


    //---------------------------根据文章id来修改文章--------------------------

    @GetMapping(value = "/{id}")
    @Operation(summary = "根据文章id来查询文章")
    //①先查询根据文章id查询对应的文章
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        ArticleByIdVo article = articleService.getInfo(id);
        return ResponseResult.okResult(article);
    }

    @PutMapping
    @Operation(summary = "修改文章")
    //②然后才是修改文章
    public ResponseResult edit(@RequestBody ArticleDTO article){
        articleService.edit(article);
        return ResponseResult.okResult();
    }

}