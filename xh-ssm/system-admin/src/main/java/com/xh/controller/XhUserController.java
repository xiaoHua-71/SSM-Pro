package com.xh.controller;

import com.xh.annotation.HasPermission;
import com.xh.annotation.HasRole;
import com.xh.annotation.Log;
import com.xh.core.RedisTemplate;
import com.xh.entity.XhUser;
import com.xh.service.XhUserService;
import com.xh.xhEnum.DeleteFlagEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户信息表(XhUser)表控制层
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@RestController
@RequestMapping("xhUser")
public class XhUserController  extends BaseController{

    @Resource
    RedisTemplate redisTemplate;
    /**
     * 服务对象
     */
    @Resource
    private XhUserService xhUserService;

    /**
     * 分页查询
     *
     * @param xhUser 筛选条件
     * @return 查询结果
     */
    @GetMapping
    @Log(title = "查询用户",businessType = "用户操作")
    public ResponseEntity<Page<XhUser>> queryByPage( XhUser xhUser) {
//        int i = 1/0;
        return ResponseEntity.ok(this.xhUserService.queryByPage(xhUser,PageRequest.of(xhUser.getPage(),xhUser.getSize())));
    }

    @GetMapping("getInfo")
    public ResponseEntity<HashMap<String, List<String>>> getInfo() {
        return ResponseEntity.ok(this.xhUserService.getInfo());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @HasPermission("system:user:query")
    public ResponseEntity<XhUser> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.xhUserService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param xhUser 实体
     * @return 新增结果
     */
    @PostMapping
    @HasRole({"admin","hr"})
    @Log(title = "创建用户",businessType = "用户操作")
    public ResponseEntity<XhUser> add(@RequestBody XhUser xhUser, HttpServletRequest request) {
        //获得ip
        xhUser.setLoginIp(request.getRemoteHost());
        xhUser.setCreateTime(new Date());
        xhUser.setCreateBy(getLoginUser().getXhUser().getUserName());
        xhUser.setStatus("0");
        //逻辑删除标志
        xhUser.setDelFlag(DeleteFlagEnum.NO.getValue());

        return ResponseEntity.ok(this.xhUserService.insert(xhUser));
    }

    /**
     * 编辑数据
     *
     * @param xhUser 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<XhUser> edit(@RequestBody XhUser xhUser) {
        return ResponseEntity.ok(this.xhUserService.update(xhUser));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {

        return ResponseEntity.ok(this.xhUserService.deleteById(id));
    }

}

