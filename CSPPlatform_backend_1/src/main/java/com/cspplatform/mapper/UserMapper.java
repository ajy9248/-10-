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

    /**
     * 根据用户id修改用户密码
     * @param uid 用户id
     * @param pwd 用户输入的新密码
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(String uid,String pwd);

    /**
     * 根据用户id查询用户数据
     * @param uid 用户id
     * @return 如果找到则返回对象，反之则返回null
     */
    Login findById(String uid);

}
