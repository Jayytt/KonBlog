package com.kon.service;

import com.kon.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.vo.LinkVo;
import com.kon.domain.vo.PageVo;
import com.kon.result.ResponseResult;

import java.util.List;

/**
 * <p>
 * 友链 服务类
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-24
 */
public interface ILinkService extends IService<Link> {

    ResponseResult<List<LinkVo>> getAllLink();

    //分页查询友链
    PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize);

}
