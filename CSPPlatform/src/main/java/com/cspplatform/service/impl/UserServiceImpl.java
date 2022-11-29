package com.cspplatform.service.impl;

import com.cspplatform.entity.Login;
import com.cspplatform.mapper.UserMapper;
import com.cspplatform.service.IUserService;
import com.cspplatform.service.ex.PasswordNotMatchException;
import com.cspplatform.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Login login(Login login) {
        Login result = userMapper.loginById(login.getUid());
        if(result == null){
            throw new UserNotFoundException("用户数据不存在");
        }

        //检测用户密码是否正确
        if(!result.getPwd().equals(login.getPwd())){
            throw new PasswordNotMatchException("用户密码错误");
        }

        Login data = new Login();
        data.setUid(result.getUid());

        return data;
    }
}
