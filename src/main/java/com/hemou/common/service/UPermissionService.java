package com.hemou.common.service;

import com.hemou.common.model.UPermission;

import java.util.List;

/**
 * (UPermission)表服务接口
 *
 * @author hemou
 * @since 2021-01-13 18:14:01
 */
public interface UPermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UPermission queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UPermission> queryAllByLimit(int offset, int limit);

    List<UPermission> selectBySearch(String search);

    /**
     * 新增数据
     *
     * @param uPermission 实例对象
     * @return 实例对象
     */
    int insert(UPermission uPermission);

    /**
     * 修改数据
     *
     * @param uPermission 实例对象
     * @return 实例对象
     */
    UPermission update(UPermission uPermission);

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteById(String ids);

}