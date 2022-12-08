package com.cspplatform.service;

import com.cspplatform.entity.Login;
import com.cspplatform.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//表示启动这个单元测试类
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private IUserService iUserService;

    @Test
    public void login(){
        Login login = new Login();
        login.setUid("920106840107");
        login.setPwd("123456");
        Login result = iUserService.login(login);
        System.out.println(result);
    }

    @Test
    public void changePassword(){
        iUserService.changePassword("0","123456","123");
    }
}

