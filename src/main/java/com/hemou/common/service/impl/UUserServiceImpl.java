package com.hemou.common.service.impl;

import com.hemou.common.dao.UUserDao;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UUserServiceImpl implements UUserService {

    @Autowired
    private UUserDao userDao;

    public int deleteByPrimaryKey(Long id){
        return userDao.deleteByPrimaryKey(id);
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
}
