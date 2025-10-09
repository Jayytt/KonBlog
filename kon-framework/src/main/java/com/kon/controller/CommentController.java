package com.kon.controller;


import com.kon.annotation.MySystemLog;
import com.kon.constant.SystemConstants;
import com.kon.domain.entity.Comment;
import com.kon.result.ResponseResult;
import com.kon.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-27
 */
@RestController
@RequestMapping("/comment")
@Tag(name = "评论相关接口")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/commentList")
    @Operation(summary = "获取评论列表")
    @MySystemLog(businessName = "获取评论列表")//自定义日志注解
    public ResponseResult commentList(String commentType, Integer pageNum, Integer pageSize, Long articleId) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, pageNum, pageSize, articleId);
    }

    @GetMapping("/linkCommentList")
    @Operation(summary = "获取友链评论列表")
    @MySystemLog(businessName = "获取友链评论列表")//自定义日志注解
    public ResponseResult linkCommentList(String commentType, Integer pageNum, Integer pageSize, Long articleId) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, pageNum, pageSize, articleId);
    }

    @PostMapping
    @Operation(summary = "发表评论")
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

}
