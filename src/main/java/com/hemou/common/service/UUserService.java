package com.hemou.common.service;

import com.hemou.common.model.UUser;

import java.util.List;

public interface UUserService {

    int deleteByPrimaryKey(String ids);

    int insert(UUser record);

    int insertSelective(UUser record);

    UUser selectByPrimaryKey(Long id);

    UUser selectByEmail(String email);

    List<UUser> selectBySearch(String search);

    UUser login(String email, String password);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);

}
