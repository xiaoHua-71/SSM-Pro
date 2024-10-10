package com.xh.controller;

import com.xh.entity.XhRoleMenu;
import com.xh.service.XhRoleMenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色和菜单关联表(XhRoleMenu)表控制层
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@RestController
@RequestMapping("xhRoleMenu")
public class XhRoleMenuController {
    /**
     * 服务对象
     */
    @Resource
    private XhRoleMenuService xhRoleMenuService;

    /**
     * 分页查询
     *
     * @param xhRoleMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<XhRoleMenu>> queryByPage(XhRoleMenu xhRoleMenu, PageRequest pageRequest) {
        return ResponseEntity.ok(this.xhRoleMenuService.queryByPage(xhRoleMenu, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<XhRoleMenu> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.xhRoleMenuService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param xhRoleMenu 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<XhRoleMenu> add(XhRoleMenu xhRoleMenu) {
        return ResponseEntity.ok(this.xhRoleMenuService.insert(xhRoleMenu));
    }

    /**
     * 编辑数据
     *
     * @param xhRoleMenu 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<XhRoleMenu> edit(XhRoleMenu xhRoleMenu) {
        return ResponseEntity.ok(this.xhRoleMenuService.update(xhRoleMenu));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.xhRoleMenuService.deleteById(id));
    }

}

