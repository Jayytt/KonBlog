package com.kon.service.impl;

import com.kon.domain.entity.ArticleTag;
import com.kon.mapper.ArticleTagMapper;
import com.kon.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章标签关联表 服务实现类
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-23
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

}
