package com.kon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.constant.SystemConstants;
import com.kon.domain.entity.Comment;
import com.kon.domain.entity.User;
import com.kon.domain.vo.CommentVo;
import com.kon.domain.vo.PageVo;
import com.kon.enums.AppHttpCodeEnum;
import com.kon.exception.SystemException;
import com.kon.mapper.CommentMapper;
import com.kon.result.ResponseResult;
import com.kon.service.ICommentService;
import com.kon.service.IUserService;
import com.kon.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 评论服务实现类
 *
 * @author 35238
 * @date 2023/7/24 0024 23:08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private IUserService userService;

    @Override
    public ResponseResult commentList(String commentType, Integer pageNum, Integer pageSize, Long articleId) {
        // 1. 查询根评论（分页）
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 筛选指定文章的评论（文章评论才需要，友链评论可能不需要）
        queryWrapper.eq(articleId != null, Comment::getArticleId, articleId);
        // 根评论的rootId为系统定义的根标识（通常为-1）
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT);
        // 筛选评论类型（文章/友链）
        queryWrapper.eq(Comment::getType, commentType);
        // 数据库层面排序，替代内存排序，提升性能
        queryWrapper.orderByDesc(Comment::getCreateTime);

        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        // 2. 转换为VO并补充用户信息（根评论）
        List<CommentVo> commentVoList = convertToCommentVoList(page.getRecords());

        // 3. 批量查询所有子评论（优化N+1查询问题）
        if (!CollectionUtils.isEmpty(commentVoList)) {
            // 获取所有根评论ID，用于批量查询子评论
            List<Long> rootIds = commentVoList.stream()
                    .map(CommentVo::getId)
                    .collect(Collectors.toList());

            // 批量查询所有子评论
            List<Comment> allChildren = list(new LambdaQueryWrapper<Comment>()
                    .in(Comment::getRootId, rootIds)
                    .orderByDesc(Comment::getCreateTime));

            // 转换子评论为VO并补充用户信息
            List<CommentVo> allChildrenVo = convertToCommentVoList(allChildren);

            // 按rootId分组，便于快速匹配
            Map<Long, List<CommentVo>> childrenGroup = allChildrenVo.stream()
                    .collect(Collectors.groupingBy(CommentVo::getRootId));

            // 为每个根评论设置子评论
            for (CommentVo commentVo : commentVoList) {
                commentVo.setChildren(childrenGroup.getOrDefault(commentVo.getId(), List.of()));
            }
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        // 评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        // 保存评论
        save(comment);
        // 返回成功响应（原代码返回null，修复为正确响应）
        return ResponseResult.okResult();
    }

    /**
     * 将Comment列表转换为CommentVo列表，并补充用户信息（用户名、被回复用户名、头像）
     */
    private List<CommentVo> convertToCommentVoList(List<Comment> commentList) {
        if (CollectionUtils.isEmpty(commentList)) {
            return List.of();
        }

        // 1. 批量获取所有相关用户ID（创建者ID + 被回复者ID）
        List<Long> userIds = commentList.stream()
                .flatMap(comment -> {
                    List<Long> ids = List.of(comment.getCreateBy());
                    if (comment.getToCommentUserId() != -1) {
                        ids = List.of(comment.getCreateBy(), comment.getToCommentUserId());
                    }
                    return ids.stream();
                })
                .distinct()
                .collect(Collectors.toList());

        // 2. 批量查询用户信息（优化：减少数据库查询次数）
        Map<Long, User> userMap = userService.listByIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        // 3. 转换为VO并设置用户信息
        return commentList.stream()
                .map(comment -> {
                    CommentVo commentVo = BeanCopyUtils.copyBean(comment, CommentVo.class);
                    // 设置评论者信息
                    User createUser = userMap.get(comment.getCreateBy());
                    commentVo.setUsername(createUser != null ? createUser.getNickName() : "未知用户");
                    commentVo.setAvatar(createUser != null ? createUser.getAvatar() : ""); // 头像设置，解决空指针

                    // 设置被回复者信息（如果存在）
                    if (comment.getToCommentUserId() != -1) {
                        User toUser = userMap.get(comment.getToCommentUserId());
                        commentVo.setToCommentUserName(toUser != null ? toUser.getNickName() : "未知用户");
                    }
                    return commentVo;
                })
                .collect(Collectors.toList());
    }
}
