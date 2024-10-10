package com.xh.service.impl;

import com.xh.entity.XhMenu;
import com.xh.dao.XhMenuDao;
import com.xh.service.XhMenuService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 菜单权限表(XhMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-10-01 13:36:44
 */
@Service("xhMenuService")
public class XhMenuServiceImpl implements XhMenuService {
    @Resource
    private XhMenuDao xhMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @Override
    public XhMenu queryById(Long menuId) {
        return this.xhMenuDao.queryById(menuId);
    }

    /**
     * 分页查询
     *
     * @param xhMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<XhMenu> queryByPage(XhMenu xhMenu, PageRequest pageRequest) {
        long total = this.xhMenuDao.count(xhMenu);
        return new PageImpl<>(this.xhMenuDao.queryAllByLimit(xhMenu, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param xhMenu 实例对象
     * @return 实例对象
     */
    @Override
    public XhMenu insert(XhMenu xhMenu) {
        this.xhMenuDao.insert(xhMenu);
        return xhMenu;
    }

    /**
     * 修改数据
     *
     * @param xhMenu 实例对象
     * @return 实例对象
     */
    @Override
    public XhMenu update(XhMenu xhMenu) {
        this.xhMenuDao.update(xhMenu);
        return this.queryById(xhMenu.getMenuId());
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long menuId) {
        return this.xhMenuDao.deleteById(menuId) > 0;
    }
}
