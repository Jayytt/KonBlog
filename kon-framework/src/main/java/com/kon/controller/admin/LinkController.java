package com.kon.controller.admin;

import com.kon.domain.entity.Link;
import com.kon.domain.vo.PageVo;
import com.kon.result.ResponseResult;
import com.kon.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 35238
 * @date 2023/8/11 0011 8:46
 */
@RestController(value = "AdminLinkController")
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private ILinkService linkService;

    //-----------------------------分页查询友链---------------------------------

    @GetMapping("/list")
    public ResponseResult list(Link link, Integer pageNum, Integer pageSize) {
        PageVo pageVo = linkService.selectLinkPage(link, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }


    //-------------------------------增加友链----------------------------------

    @PostMapping
    public ResponseResult add(@RequestBody Link link) {
        linkService.save(link);
        return ResponseResult.okResult();
    }


    //-------------------------------修改友链---------------------------------

    @GetMapping(value = "/{id}")
    //①根据id查询友链
    public ResponseResult getInfo(@PathVariable(value = "id") Long id) {
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

    @PutMapping("/changeLinkStatus")
    //②修改友链状态
    public ResponseResult changeLinkStatus(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @PutMapping
    //③修改友链
    public ResponseResult edit(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping
    public ResponseResult delete(@RequestParam("ids") List<Long> ids){
        linkService.removeByIds(ids);
        return ResponseResult.okResult();
    }
}