package com.xh.service;

import com.xh.entity.XhRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 角色信息表(XhRole)表服务接口
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public interface XhRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    XhRole queryById(Long roleId);

    /**
     * 分页查询
     *
     * @param xhRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<XhRole> queryByPage(XhRole xhRole, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param xhRole 实例对象
     * @return 实例对象
     */
    XhRole insert(XhRole xhRole);

    /**
     * 修改数据
     *
     * @param xhRole 实例对象
     * @return 实例对象
     */
    XhRole update(XhRole xhRole);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    boolean deleteById(Long roleId);

}
