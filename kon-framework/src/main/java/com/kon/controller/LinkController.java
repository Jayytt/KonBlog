package com.kon.controller;


import com.kon.annotation.MySystemLog;
import com.kon.domain.entity.Link;
import com.kon.domain.vo.LinkVo;
import com.kon.result.ResponseResult;
import com.kon.service.ILinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 友链 前端控制器
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-24
 */
@RestController
@RequestMapping("/link")
@Tag(name = "友链相关接口")
@Slf4j
public class LinkController {
    @Autowired
    private ILinkService linkService;

    @Operation(summary = "查询所有友链")
    @GetMapping("getAllLink")
    @MySystemLog(businessName = "查询所有友链")//自定义日志注解
    public ResponseResult<List<LinkVo>> getAllLink() {
        log.info("获取所有友链");
        return linkService.getAllLink();
    }
}
