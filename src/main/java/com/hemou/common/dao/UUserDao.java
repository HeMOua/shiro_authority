package com.hemou.common.dao;

import com.hemou.common.model.UUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(UUser record);

    int insertSelective(UUser record);

    UUser selectByPrimaryKey(Long id);

    UUser selectByEmail(String email);

    List<UUser> selectBySearch(@Param("search") String search);

    UUser login(String email, String password);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);

    int allocRole(@Param("uid") Long uid, @Param("rid") Long rid);

    int cancelRole(@Param("uid") Long uid);

}