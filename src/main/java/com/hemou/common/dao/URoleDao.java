package com.hemou.common.dao;

import com.hemou.common.model.URole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (URole)表数据库访问层
 *
 * @author hemou
 * @since 2021-01-12 18:46:11
 */
public interface URoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    URole queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<URole> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<URole> selectBySearch(@Param("search") String search);

    List<URole> selectByUserId(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param uRole 实例对象
     * @return 对象列表
     */
    List<URole> queryAll(URole uRole);

    /**
     * 新增数据
     *
     * @param uRole 实例对象
     * @return 影响行数
     */
    int insert(URole uRole);

    /**
     * 修改数据
     *
     * @param uRole 实例对象
     * @return 影响行数
     */
    int update(URole uRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    int cancelPermission(@Param("rid") Long rid);

    int allocPermission(@Param("rid") Long rid, @Param("pid") Long pid);
}