package com.xh.controller;

import com.xh.entity.XhMenu;
import com.xh.service.XhMenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜单权限表(XhMenu)表控制层
 *
 * @author makejava
 * @since 2024-10-01 13:36:44
 */
@RestController
@RequestMapping("xhMenu")
public class XhMenuController {
    /**
     * 服务对象
     */
    @Resource
    private XhMenuService xhMenuService;

    /**
     * 分页查询
     *
     * @param xhMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<XhMenu>> queryByPage(XhMenu xhMenu, PageRequest pageRequest) {
        return ResponseEntity.ok(this.xhMenuService.queryByPage(xhMenu, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<XhMenu> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.xhMenuService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param xhMenu 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<XhMenu> add(XhMenu xhMenu) {
        return ResponseEntity.ok(this.xhMenuService.insert(xhMenu));
    }

    /**
     * 编辑数据
     *
     * @param xhMenu 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<XhMenu> edit(XhMenu xhMenu) {
        return ResponseEntity.ok(this.xhMenuService.update(xhMenu));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.xhMenuService.deleteById(id));
    }

}

