package com.xh.service;

import com.xh.entity.XhRoleMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 角色和菜单关联表(XhRoleMenu)表服务接口
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public interface XhRoleMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    XhRoleMenu queryById(Long roleId);

    /**
     * 分页查询
     *
     * @param xhRoleMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<XhRoleMenu> queryByPage(XhRoleMenu xhRoleMenu, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param xhRoleMenu 实例对象
     * @return 实例对象
     */
    XhRoleMenu insert(XhRoleMenu xhRoleMenu);

    /**
     * 修改数据
     *
     * @param xhRoleMenu 实例对象
     * @return 实例对象
     */
    XhRoleMenu update(XhRoleMenu xhRoleMenu);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    boolean deleteById(Long roleId);

}
