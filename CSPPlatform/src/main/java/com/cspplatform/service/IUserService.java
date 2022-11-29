package com.cspplatform.service;

import com.cspplatform.entity.Login;

public interface IUserService {
    /**
     * 用户登录
     * @param login 登录信息
     * @return 用户登录信息
     */
    Login login(Login login);
}
