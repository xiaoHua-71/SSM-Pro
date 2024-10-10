package com.xh.dao;

import com.xh.entity.XhUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户信息表(XhUser)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public interface XhUserDao {
    /**
     * 通过用户名查询用户*
     * @param userName
     * @return
     */
     XhUser queryByUserName(String userName);

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    XhUser queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param xhUser 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<XhUser> queryAllByLimit(@Param("xhUser") XhUser xhUser, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param xhUser 查询条件
     * @return 总行数
     */
    long count(XhUser xhUser);

    /**
     * 新增数据
     *
     * @param xhUser 实例对象
     * @return 影响行数
     */
    int insert(XhUser xhUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<XhUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<XhUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<XhUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<XhUser> entities);

    /**
     * 修改数据
     *
     * @param xhUser 实例对象
     * @return 影响行数
     */
    int update(XhUser xhUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

    /**
     * 通过用户id查询角色和权限的信息
     * @param UserId
     * @return
     */

    XhUser getInfo(Long UserId);

}

