package com.kon.controller.admin;

import com.kon.domain.dto.AddTagDTO;
import com.kon.domain.dto.TagListDTO;
import com.kon.domain.vo.PageVo;
import com.kon.result.ResponseResult;
import com.kon.service.TagService;
import com.kon.utils.BeanCopyUtils;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "标签模块", description = "标签相关接口")
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    //TagService是我们在huanf-framework工程的接口
    private TagService tagService;

    //查询标签列表
    @GetMapping("/list")
    @Operation(summary = "查询标签列表")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDTO tagListDto) {
        //pageTagList是我们在huanf-framework工程写的方法
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    @Operation(summary = "新增标签")
    public ResponseResult add(@RequestBody AddTagDTO tagDTO) {
        com.kon.domain.entity.Tag tag = BeanCopyUtils.copyBean(tagDTO, com.kon.domain.entity.Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }
}