package com.kon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kon.constant.SystemConstants;
import com.kon.domain.entity.Link;
import com.kon.domain.vo.LinkVo;
import com.kon.domain.vo.PageVo;
import com.kon.mapper.LinkMapper;
import com.kon.result.ResponseResult;
import com.kon.service.ILinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 友链 服务实现类
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-24
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {

    @Override
    public ResponseResult<List<LinkVo>> getAllLink() {
        //查询所有友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL)
                .eq(Link::getDelFlag, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //把查询的结果封装成LinkVo
        List<LinkVo> linkVo = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    //-----------------------------分页查询友链---------------------------------

    @Override
    public PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(link.getName()),Link::getName, link.getName());
        queryWrapper.eq(Objects.nonNull(link.getStatus()),Link::getStatus, link.getStatus());

        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Link> categories = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(categories);
        return pageVo;
    }
}
