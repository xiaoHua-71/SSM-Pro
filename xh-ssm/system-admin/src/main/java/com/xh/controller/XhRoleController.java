package com.xh.controller;

import com.xh.entity.XhRole;
import com.xh.service.XhRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色信息表(XhRole)表控制层
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@RestController
@RequestMapping("xhRole")
public class XhRoleController {
    /**
     * 服务对象
     */
    @Resource
    private XhRoleService xhRoleService;

    /**
     * 分页查询
     *
     * @param xhRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<XhRole>> queryByPage(XhRole xhRole, PageRequest pageRequest) {
        return ResponseEntity.ok(this.xhRoleService.queryByPage(xhRole, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<XhRole> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.xhRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param xhRole 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<XhRole> add(XhRole xhRole) {
        return ResponseEntity.ok(this.xhRoleService.insert(xhRole));
    }

    /**
     * 编辑数据
     *
     * @param xhRole 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<XhRole> edit(XhRole xhRole) {
        return ResponseEntity.ok(this.xhRoleService.update(xhRole));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.xhRoleService.deleteById(id));
    }

}

