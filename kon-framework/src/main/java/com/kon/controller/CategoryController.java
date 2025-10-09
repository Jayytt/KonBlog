package com.kon.controller;
import com.kon.annotation.MySystemLog;
import com.kon.result.ResponseResult;
import com.kon.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-23
 */
@RestController
@RequestMapping("/category")
@Tag(name = "分类相关接口")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Operation(summary = "查询分类列表")
    @GetMapping("/getCategoryList")
    @MySystemLog(businessName = "查询分类列表")//自定义日志注解
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();
    }

}
