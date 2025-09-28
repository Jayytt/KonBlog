package com.kon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.constant.SystemConstants;
import com.kon.domain.entity.Comment;
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
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 35238
 * @date 2023/7/24 0024 23:08
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    //根据userid查询用户信息，也就是查username
    private IUserService userService;

    @Override
    public ResponseResult commentList(String commetnType,Integer pageNum, Integer pageSize, Long articleId) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        //对articleId进行判断，作用是得到指定的文章
        queryWrapper.eq(Comment::getArticleId, articleId);

        //对评论区的某条评论的rootID进行判断，如果为-1，就表示是根评论。SystemCanstants是我们写的解决字面值的类
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT);

        //文章的评论，避免得到友链的评论
        queryWrapper.eq(Comment::getType,commetnType);

        //分页查询。查的是整个评论区的每一条评论
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        //调用下面那个方法。根评论排序
        List<Comment> sortedComments = page.getRecords().stream()
                .sorted(Comparator.comparing(Comment::getCreateTime).reversed())
                .collect(Collectors.toList());
        List<CommentVo> commentVoList = xxToCommentList(sortedComments);

        //遍历(可以用for循环，也可以用stream流)。查询子评论(注意子评论只查到二级评论，不再往深查)
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //把查到的children子评论集的集合，赋值给commentVo类的children字段
            commentVo.setChildren(children);

        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        //mp的save方法往数据库插入数据（用户发送的评论的各个字段）
        save(comment);
        return null;
    }

    //根据根评论的id，来查询对应的所有子评论(注意子评论只查到二级评论，不再往深查)
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        //对子评论按照时间进行排序
        queryWrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        //调用下面那个方法
        List<CommentVo> commentVos = xxToCommentList(comments);
        return commentVos;
    }

    //封装响应返回。CommentVo、BeanCopyUtils、ResponseResult、PageVo是我们写的类
    private List<CommentVo> xxToCommentList(List<Comment> list) {
        //获取评论区的所有评论
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历(可以用for循环，也可以用stream流)。由于封装响应好的数据里面没有username字段，所以我们还不能返回给前端。这个遍历就是用来得到username字段
        for (CommentVo commentVo : commentVos) {
            //
            //需要根据commentVo类里面的createBy字段，然后用createBy字段去查询user表的nickname字段(子评论的用户昵称)
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            //然后把nickname字段(发这条子评论的用户昵称)的数据赋值给commentVo类的username字段
            commentVo.setUsername(nickName);

            //查询根评论的用户昵称。怎么判断是根评论的用户呢，判断toCommentId为1，就表示这条评论是根评论
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                //然后把nickname字段(发这条根评论的用户昵称)的数据赋值给commentVo类的toCommentUserName字段
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }

        //返回给前端
        return commentVos;
    }


}