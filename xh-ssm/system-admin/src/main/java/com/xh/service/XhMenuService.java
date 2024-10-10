package com.xh.service;

import com.xh.entity.XhMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 菜单权限表(XhMenu)表服务接口
 *
 * @author makejava
 * @since 2024-10-01 13:36:44
 */
public interface XhMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    XhMenu queryById(Long menuId);

    /**
     * 分页查询
     *
     * @param xhMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<XhMenu> queryByPage(XhMenu xhMenu, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param xhMenu 实例对象
     * @return 实例对象
     */
    XhMenu insert(XhMenu xhMenu);

    /**
     * 修改数据
     *
     * @param xhMenu 实例对象
     * @return 实例对象
     */
    XhMenu update(XhMenu xhMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    boolean deleteById(Long menuId);

}
