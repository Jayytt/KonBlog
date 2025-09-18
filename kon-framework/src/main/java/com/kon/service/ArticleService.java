package com.kon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.entity.Article;

import java.util.List;

public interface ArticleService  {
    List<Article> list();
}
