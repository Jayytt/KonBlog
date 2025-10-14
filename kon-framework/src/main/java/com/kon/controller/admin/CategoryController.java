package com.kon.controller.admin;

import com.kon.domain.dto.CategoryDTO;
import com.kon.domain.entity.Category;
import com.kon.domain.vo.CategoryVo;
import com.kon.domain.vo.PageVo;
import com.kon.result.ResponseResult;
import com.kon.service.ICategoryService;
import com.kon.utils.BeanCopyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController(value = "adminCategoryController")
@RequestMapping("/content/category")
@Tag(name = "后台分类管理相关接口")
public class CategoryController {

    //---------------------------写博文-查询文章分类的接口--------------------------------

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    //----------------------------分页查询分类列表-------------------------------------

    @GetMapping("/list")
    public ResponseResult list(Category category, Integer pageNum, Integer pageSize) {
        PageVo pageVo = categoryService.selectCategoryPage(category, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }

//-----------------------------增加文章的分类--------------------------------------

    @PostMapping
    @Operation(summary = "新增文章分类")
    public ResponseResult add(@RequestBody CategoryDTO categoryDTO) {
        Category category = BeanCopyUtils.copyBean(categoryDTO, Category.class);
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    //删除文章分类
    @DeleteMapping
    @Operation(summary = "删除文章分类")
    public ResponseResult remove(@RequestParam("ids") List<Long> ids) {
        categoryService.removeByIds(ids);
        return ResponseResult.okResult();
    }

    //-----------------------------修改文章的分类--------------------------------------

    @GetMapping(value = "/{id}")
    @Operation(summary = "根据分类id查询分类信息")
    //①根据分类的id来查询分类
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    @PutMapping
    @Operation(summary = "根据id修改分类")
    //②根据分类的id来修改分类
    public ResponseResult edit(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }
}