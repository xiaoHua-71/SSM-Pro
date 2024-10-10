package com.xh.controller;

import com.xh.entity.XhUserRole;
import com.xh.service.XhUserRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户和角色关联表(XhUserRole)表控制层
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@RestController
@RequestMapping("xhUserRole")
public class XhUserRoleController {
    /**
     * 服务对象
     */
    @Resource
    private XhUserRoleService xhUserRoleService;

    /**
     * 分页查询
     *
     * @param xhUserRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<XhUserRole>> queryByPage(XhUserRole xhUserRole, PageRequest pageRequest) {
        return ResponseEntity.ok(this.xhUserRoleService.queryByPage(xhUserRole, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<XhUserRole> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.xhUserRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param xhUserRole 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<XhUserRole> add(XhUserRole xhUserRole) {
        return ResponseEntity.ok(this.xhUserRoleService.insert(xhUserRole));
    }

    /**
     * 编辑数据
     *
     * @param xhUserRole 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<XhUserRole> edit(XhUserRole xhUserRole) {
        return ResponseEntity.ok(this.xhUserRoleService.update(xhUserRole));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.xhUserRoleService.deleteById(id));
    }

}

