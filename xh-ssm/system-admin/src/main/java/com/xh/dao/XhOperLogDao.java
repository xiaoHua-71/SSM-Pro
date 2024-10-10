package com.xh.dao;

import com.xh.entity.XhOperLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 操作日志(XhOperLog)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public interface XhOperLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param operId 主键
     * @return 实例对象
     */
    XhOperLog queryById(Integer operId);

    /**
     * 查询指定行数据
     *
     * @param xhOperLog 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<XhOperLog> queryAllByLimit(@Param("xhOperLog")XhOperLog xhOperLog, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param xhOperLog 查询条件
     * @return 总行数
     */
    long count(XhOperLog xhOperLog);

    /**
     * 新增数据
     *
     * @param xhOperLog 实例对象
     * @return 影响行数
     */
    int insert(XhOperLog xhOperLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<XhOperLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<XhOperLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<XhOperLog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<XhOperLog> entities);

    /**
     * 修改数据
     *
     * @param xhOperLog 实例对象
     * @return 影响行数
     */
    int update(XhOperLog xhOperLog);

    /**
     * 通过主键删除数据
     *
     * @param operId 主键
     * @return 影响行数
     */
    int deleteById(Integer operId);

}

