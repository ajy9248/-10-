package com.cspplatform.controller;

import com.cspplatform.service.ex.*;
import com.cspplatform.util.JsonResult;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {

    /** 操作成功的状态码 */
    public  static  final int ok = 200;

    //请求处理方法，这个方法的返回值就是需要传递给全端的数据
    //自动将异常对象传递给此方法的参数列表上
    //当项目中产生了异常，被统一拦截到此方法中，这个方法此时充当请求处理方法，方法的返回值直接给到前端
    @ExceptionHandler({ServiceException.class, FileUploadException.class}) //用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户数据不存在的异常");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("用户的密码错误的异常");
        }
        else if(e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新数据时产生未知的异常");
        }
        else if(e instanceof IdNotFoundException){
            result.setState(5004);
            result.setMessage("删除数据时产生未知的异常");
        }

        return result;
    }

    /**
     * 获取session对象中的id
     * @param session 对象
     * @return 当前用户的id值
     */
    protected final String getIdFromSession(HttpSession session){
        return session.getAttribute("id").toString();
    }

    protected final String getSessionFromSession(HttpSession ssession){
        return ssession.getAttribute("session").toString();
    }
}
