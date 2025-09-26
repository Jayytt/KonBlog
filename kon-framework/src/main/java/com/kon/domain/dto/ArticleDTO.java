package com.kon.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 文章数据传输对象，用于文章相关操作的数据传递
 */
@Data
public class ArticleDTO {

    private Long id;

    private String title;

    private String content;

    private String summary;

    private Long categoryId;

    private String thumbnail;

    private String isTop;

    private String status;

    private Long viewCount;

    private String isComment;

    private Date createTime;

    private Date updateTime;
}