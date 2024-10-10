package com.xh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xh.entity.XhLoginUser;
import com.xh.entity.XhUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;

/**
 * 用户信息表(XhUser)表服务接口
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public interface XhUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    XhUser queryById(Long userId);

    /**
     * 分页查询
     *
     * @param xhUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<XhUser> queryByPage(XhUser xhUser, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param xhUser 实例对象
     * @return 实例对象
     */
    XhUser insert(XhUser xhUser);

    /**
     * 修改数据
     *
     * @param xhUser 实例对象
     * @return 实例对象
     */
    XhUser update(XhUser xhUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

    /**
     *使用用户名和密码登录 *
     * @param userName
     * @param password
     * @return
     */

    XhLoginUser login(String userName, String password) throws JsonProcessingException;

    /**
     * 退出的方法*
     */
    void logout();

    /**
     *获取用户的所以信息
     * @return
     */
    HashMap<String, List<String>> getInfo();
}
