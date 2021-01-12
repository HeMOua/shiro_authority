package com.hemou.common.service.impl;

import com.hemou.common.dao.URoleDao;
import com.hemou.common.model.URole;
import com.hemou.common.model.UUser;
import com.hemou.common.service.URoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (URole)表服务实现类
 *
 * @author hemou
 * @since 2021-01-12 18:46:13
 */
@Service("roleService")
public class URoleServiceImpl implements URoleService {
    @Resource
    private URoleDao roleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public URole queryById(Long id) {
        return this.roleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<URole> queryAllByLimit(int offset, int limit) {
        return this.roleDao.queryAllByLimit(offset, limit);
    }

    public List<URole> selectBySearch(String search){
        return roleDao.selectBySearch(search);
    }

    /**
     * 新增数据
     *
     * @param uRole 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(URole uRole) {
        return roleDao.insert(uRole);
    }

    /**
     * 修改数据
     *
     * @param uRole 实例对象
     * @return 实例对象
     */
    @Override
    public URole update(URole uRole) {
        this.roleDao.update(uRole);
        return this.queryById(uRole.getId());
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

                int delete = roleDao.deleteById(id);

                if (delete == 0) throw new RuntimeException("删除失败，请重试！");
                else count++;
            }
            return count;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<URole> selectByUserId(Long id){
        return roleDao.selectByUserId(id);
    }


    @Override
    public void fillByUser(UUser user){
        List<URole> roles = roleDao.selectByUserId(user.getId());
        user.setRoleList(roles);
    }

    @Override
    public void fillByUser(List<UUser> users) {
        for(UUser u : users){
            fillByUser(u);
        }
    }

}