package com.xh.dao;

import com.xh.entity.XhUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户和角色关联表(XhUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public interface XhUserRoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    XhUserRole queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param xhUserRole 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<XhUserRole> queryAllByLimit(XhUserRole xhUserRole, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param xhUserRole 查询条件
     * @return 总行数
     */
    long count(XhUserRole xhUserRole);

    /**
     * 新增数据
     *
     * @param xhUserRole 实例对象
     * @return 影响行数
     */
    int insert(XhUserRole xhUserRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<XhUserRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<XhUserRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<XhUserRole> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<XhUserRole> entities);

    /**
     * 修改数据
     *
     * @param xhUserRole 实例对象
     * @return 影响行数
     */
    int update(XhUserRole xhUserRole);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

}

