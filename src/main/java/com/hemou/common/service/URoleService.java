package com.hemou.common.service;

import com.hemou.common.model.URole;
import com.hemou.common.model.UUser;

import java.util.List;

/**
 * (URole)表服务接口
 *
 * @author hemou
 * @since 2021-01-12 18:46:12
 */
public interface URoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    URole queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<URole> queryAllByLimit(int offset, int limit);


    List<URole> selectBySearch(String search);

    /**
     * 新增数据
     *
     * @param uRole 实例对象
     * @return 实例对象
     */
    int insert(URole uRole);

    /**
     * 修改数据
     *
     * @param uRole 实例对象
     * @return 实例对象
     */
    URole update(URole uRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    int deleteById(String id);

    List<URole> selectByUserId(Long id);

    void fillByUser(UUser user);

    void fillByUser(List<UUser> users);

    void allocPermission(Long rid, String pids);

    void cancelPermission(String rids);

}