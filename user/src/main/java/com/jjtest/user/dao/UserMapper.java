package com.jjtest.user.dao;

import com.jjtest.user.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户
     * @param po
     * @return
     */
    UserPO selectUser(UserPO po);

    int batchInsertUser(List<UserPO> list);

    int updateUser(UserPO userPO);
}