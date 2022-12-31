package com.cspplatform.controller;

import com.cspplatform.entity.Login;
import com.cspplatform.service.IUserService;
import com.cspplatform.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    public JsonResult<Login> login(Login login, HttpSession session){
        Login data = userService.login(login);

        //向session对象中完成数据的绑定
        session.setAttribute("id",data.getUid());

        //获取session中绑定的数据
        System.out.println(getIdFromSession(session));

        return new JsonResult<>(ok,data);
    }

    @RequestMapping("/modifyPwd")
    public JsonResult<Void> changePassword(String uid,String old_pwd,String new_pwd){
        userService.changePassword(uid,old_pwd,new_pwd);
        return new JsonResult<>(ok);
    }

}
