package com.jjtest.taskcenter.dao.user;

import com.jjtest.taskcenter.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    /**
     * 查询用户
     * @param po
     * @return
     */
    UserPO selectUser(UserPO po);

}
