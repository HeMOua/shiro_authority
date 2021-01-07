package com.hemou.common.dao;

import com.hemou.common.model.UUser;

public interface UUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(UUser record);

    int insertSelective(UUser record);

    UUser selectByPrimaryKey(Long id);

    UUser selectByEmail(String email);

    UUser login(String email, String password);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);
}