package com.jjtest.user.dao;

import com.jjtest.user.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

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



}