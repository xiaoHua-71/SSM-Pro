package com.xh.controller;

import com.xh.entity.XhOperLog;
import com.xh.service.XhOperLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 操作日志(XhOperLog)表控制层
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@RestController
@RequestMapping("xhOperLog")
public class XhOperLogController {
    /**
     * 服务对象
     */
    @Resource
    private XhOperLogService xhOperLogService;

    /**
     * 分页查询
     *
     * @param xhOperLog 筛选条件
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<XhOperLog>> queryByPage(XhOperLog xhOperLog, int page,int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(this.xhOperLogService.queryByPage(xhOperLog, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<XhOperLog> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.xhOperLogService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param xhOperLog 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<String> add(XhOperLog xhOperLog) {
        return ResponseEntity.ok().build();
    }

    /**
     * 编辑数据
     *
     * @param xhOperLog 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<XhOperLog> edit(XhOperLog xhOperLog) {
        return ResponseEntity.ok(this.xhOperLogService.update(xhOperLog));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.xhOperLogService.deleteById(id));
    }

}

