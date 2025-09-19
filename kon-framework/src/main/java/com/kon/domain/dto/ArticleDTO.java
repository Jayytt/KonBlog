package com.kon.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 文章数据传输对象，用于文章相关操作的数据传递
 */
@Data
@ApiModel(description = "文章传输对象")
public class ArticleDTO {

    @ApiModelProperty(value = "文章ID")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章摘要")
    private String summary;

    @ApiModelProperty(value = "所属分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "文章缩略图URL")
    private String thumbnail;

    @ApiModelProperty(value = "是否置顶（0否，1是）")
    private String isTop;

    @ApiModelProperty(value = "状态（0已发布，1草稿）")
    private String status;

    @ApiModelProperty(value = "访问量（前端无需传递，由系统自动维护）")
    private Long viewCount;

    @ApiModelProperty(value = "是否允许评论（1是，0否）")
    private String isComment;

    @ApiModelProperty(value = "创建时间（前端无需传递，由系统自动维护）")
    private Date createTime;

    @ApiModelProperty(value = "更新时间（前端无需传递，由系统自动维护）")
    private Date updateTime;
}