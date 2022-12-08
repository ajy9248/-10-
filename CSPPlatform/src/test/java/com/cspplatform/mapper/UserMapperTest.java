package com.cspplatform.mapper;

import com.cspplatform.entity.Login;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.rmi.server.UID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void login(){
        Login login = userMapper.loginById("920106840107");
        System.out.println(login);
    }

    @Test
    public void updatePasswordByUid(){
       Integer i=userMapper.updatePasswordByUid("0","654321");
    }

    @Test
    public void findById(){
        System.out.println(userMapper.findById("0"));
    }
}
