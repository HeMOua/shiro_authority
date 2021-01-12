package com.hemou.common.service.impl;

import com.hemou.common.dao.UPermissionDao;
import com.hemou.common.model.UPermission;
import com.hemou.common.service.UPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UPermission)表服务实现类
 *
 * @author hemou
 * @since 2021-01-13 18:14:02
 */
@Service("uPermissionService")
public class UPermissionServiceImpl implements UPermissionService {
    @Resource
    private UPermissionDao uPermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UPermission queryById(Long id) {
        return this.uPermissionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UPermission> queryAllByLimit(int offset, int limit) {
        return this.uPermissionDao.queryAllByLimit(offset, limit);
    }

    public List<UPermission> selectBySearch(String search){
        return uPermissionDao.selectBySearch(search);
    }


    /**
     * 新增数据
     *
     * @param uPermission 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(UPermission uPermission) {
        return uPermissionDao.insert(uPermission);
    }

    /**
     * 修改数据
     *
     * @param uPermission 实例对象
     * @return 实例对象
     */
    @Override
    public UPermission update(UPermission uPermission) {
        this.uPermissionDao.update(uPermission);
        return this.queryById(uPermission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(String ids) {
        try {
            int count = 0;
            String[] idList = ids.split(",");
            for (String s : idList) {
                Long id = Long.valueOf(s);

                int delete = uPermissionDao.deleteById(id);

                if (delete == 0) throw new RuntimeException("删除失败，请重试！");
                else count++;
            }
            return count;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}