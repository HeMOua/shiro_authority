package com.hemou.common.service.impl;

import com.hemou.common.dao.UUserDao;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import com.hemou.core.shiro.token.TokenManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UUserServiceImpl implements UUserService {

    @Resource
    private UUserDao userDao;

    public int deleteByPrimaryKey(String ids){
        try {
            int count = 0;
            String[] idList = ids.split(",");
            for (String s : idList) {
                Long id = Long.valueOf(s);
                Long userId = TokenManager.getUser().getId();
                if(id.equals(userId)) throw new RuntimeException("删除失败，不能删除自己！");

                int delete = userDao.deleteByPrimaryKey(id);

                if (delete == 0) throw new RuntimeException("删除失败，请重试！");
                else count++;
            }
            return count;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public int insert(UUser record){
        return userDao.insert(record);
    }

    public int insertSelective(UUser record){
        return userDao.insertSelective(record);
    }

    public UUser selectByPrimaryKey(Long id){
        return userDao.selectByPrimaryKey(id);
    }

    public UUser selectByEmail(String email){
        return userDao.selectByEmail(email);
    }

    public List<UUser> selectBySearch(String search){
        return userDao.selectBySearch(search);
    }

    public UUser login(String email, String password){
        return userDao.login(email, password);
    }

    public int updateByPrimaryKeySelective(UUser record){
        return userDao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UUser record){
        return userDao.updateByPrimaryKey(record);
    }

    public void allocRole(Long uid, String rids){
        try {
            this.cancelRole(String.valueOf(uid));
            if(StringUtils.isEmpty(rids)) return;
            String[] idList = rids.split(",");
            for (String s : idList) {
                Long id = Long.valueOf(s);
                int insert = userDao.allocRole(uid, id);
                if(insert != 1) throw new RuntimeException("分配失败，请重试！");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void cancelRole(String ids){
        try {
            String[] idList = ids.split(",");
            for (String s : idList) {
                Long id = Long.valueOf(s);
                userDao.cancelRole(id);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
