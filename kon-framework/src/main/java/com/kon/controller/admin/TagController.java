package com.kon.controller.admin;

import com.kon.domain.dto.AddTagDTO;
import com.kon.domain.dto.EditTagDTO;
import com.kon.domain.dto.TagListDTO;
import com.kon.domain.vo.PageVo;
import com.kon.result.ResponseResult;
import com.kon.service.TagService;
import com.kon.utils.BeanCopyUtils;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @DeleteMapping // 路径为 /content/tag，通过查询参数传ids
    @Operation(summary = "删除标签")
    public ResponseResult deleteTags(@RequestParam("ids") List<Long> ids) {
        tagService.removeByIds(ids); // MyBatis-Plus 提供的批量删除方法
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询标签")
    //根据标签的id来查询标签
    public ResponseResult getInfo(@PathVariable(value = "id") Long id) {
        com.kon.domain.entity.Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }

    @PutMapping
    @Operation(summary = "修改标签")
    //根据标签的id来修改标签
    public ResponseResult edit(@RequestBody EditTagDTO tagDTO) {
        com.kon.domain.entity.Tag tag = BeanCopyUtils.copyBean(tagDTO, com.kon.domain.entity.Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }
}