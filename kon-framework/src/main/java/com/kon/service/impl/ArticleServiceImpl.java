package com.kon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.constant.SystemConstants;
import com.kon.domain.entity.Article;
import com.kon.domain.vo.HotArticleVO;
import com.kon.mapper.ArticleMapper;
import com.kon.result.ResponseResult;
import com.kon.service.IArticleService;
import com.kon.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    private final ArticleMapper articLeMapper;


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        //Bean拷贝
        List<HotArticleVO> articleVOS = BeanCopyUtils.copyBeanList(articles, HotArticleVO.class);
       /* for (Article article : articles) {
            HotArticleVO articleVO = new HotArticleVO();
            BeanUtils.copyProperties(article, articleVO);
            articleVOS.add(articleVO);
        }*/
        return ResponseResult.okResult(articleVOS);
    }
}
