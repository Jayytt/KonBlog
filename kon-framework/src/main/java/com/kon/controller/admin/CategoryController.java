package com.kon.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.kon.domain.dto.CategoryDTO;
import com.kon.domain.entity.Category;
import com.kon.domain.vo.CategoryVo;
import com.kon.domain.vo.ExcelCategoryVo;
import com.kon.domain.vo.PageVo;
import com.kon.enums.AppHttpCodeEnum;
import com.kon.result.ResponseResult;
import com.kon.service.ICategoryService;
import com.kon.utils.BeanCopyUtils;
import com.kon.utils.WebUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseResult getInfo(@PathVariable(value = "id") Long id) {
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    @PutMapping
    @Operation(summary = "根据id修改分类")
    //②根据分类的id来修改分类
    public ResponseResult edit(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }



    //---------------------------把分类数据写入到Excel并导出-----------------------------

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")//权限控制，ps是PermissionService类的bean名称


    //注意返回值类型是void
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头，下载下来的Excel文件叫'分类.xlsx'
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<Category> xxcategory = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(xxcategory, ExcelCategoryVo.class);
            //把数据写入到Excel中，也就是把ExcelCategoryVo实体类的字段作为Excel表格的列头
            //sheet方法里面的字符串是Excel表格左下角工作簿的名字
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("文章分类")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常,就返回失败的json数据给前端。AppHttpCodeEnum和ResponseResult是我们在huanf-framework工程写的类
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            //WebUtils是我们在huanf-framework工程写的类，里面的renderString方法是将json字符串写入到请求体，然后返回给前端
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
