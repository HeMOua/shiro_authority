package com.hemou.common.dao;

import com.hemou.common.model.UPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (UPermission)表数据库访问层
 *
 * @author hemou
 * @since 2021-01-13 18:14:01
 */
public interface UPermissionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UPermission queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UPermission> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<UPermission> selectBySearch(@Param("search") String search);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param uPermission 实例对象
     * @return 对象列表
     */
    List<UPermission> queryAll(UPermission uPermission);

    /**
     * 新增数据
     *
     * @param uPermission 实例对象
     * @return 影响行数
     */
    int insert(UPermission uPermission);

    /**
     * 修改数据
     *
     * @param uPermission 实例对象
     * @return 影响行数
     */
    int update(UPermission uPermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    List<UPermission> selectByRoleId(@Param("id") Long id);

}