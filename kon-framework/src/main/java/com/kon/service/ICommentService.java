package com.kon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.entity.Comment;
import com.kon.result.ResponseResult;

/**
 * @author 35238
 * @date 2023/7/24 0024 23:08
 */
public interface ICommentService extends IService<Comment> {

    //查询评论区的评论。增加了commentType参数，用于区分文章的评论区、友链的评论区
    ResponseResult commentList(String commentType,  Integer pageNum, Integer pageSize,Long articleId);

    //在文章的评论区发送评论
    ResponseResult addComment(Comment comment);
}