package com.kon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.domain.entity.Article;
import com.kon.mapper.ArticleMapper;
import com.kon.service.ArticleService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl  implements ArticleService {

    private final ArticleMapper articLeMapper;

    @Override
    public List<Article> list() {
        System.out.println("list");
        return articLeMapper.selectList();
    }

}
