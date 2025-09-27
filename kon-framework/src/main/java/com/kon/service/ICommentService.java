package com.kon.service;

import com.kon.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.result.ResponseResult;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-27
 */
public interface ICommentService extends IService<Comment> {

//    查询评论列表
    ResponseResult commentList(Integer pageNum, Integer pageSize, Integer articleId);

    //在文章的评论区发送评论
    ResponseResult addComment(Comment comment);
}
