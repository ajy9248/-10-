package com.cspplatform.service.impl;

import com.cspplatform.entity.Login;
import com.cspplatform.mapper.UserMapper;
import com.cspplatform.service.IUserService;
import com.cspplatform.service.ex.PasswordNotMatchException;
import com.cspplatform.service.ex.UpdateException;
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

    @Override
    public void changePassword(String uid, String old_pwd, String new_pwd) {
        Login result=userMapper.findById(uid);
        if(result==null)
            throw new UserNotFoundException("用户数据不存在");
        //原始密码与数据库中密码比较
        if(!old_pwd.equals(result.getPwd()))
            throw new PasswordNotMatchException("密码错误");
        //将新的密码设置进去
        Integer i=userMapper.updatePasswordByUid(uid,new_pwd);
        if(1 != i )
            throw new UpdateException("更新数据时产生未知的异常");
    }
}
