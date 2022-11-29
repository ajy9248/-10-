package com.cspplatform.mapper;

import com.cspplatform.entity.Login;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 根据用户id查询用户登录
     * @param uid 用户id
     * @return 用户数据/null
     */
    Login loginById(String uid);
}
