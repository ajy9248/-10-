package com.cspplatform.controller;

import com.cspplatform.entity.Teacher;
import com.cspplatform.service.ITeacherService;
import com.cspplatform.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("teacher")
public class TeacherController extends BaseController{
    @Autowired
    private ITeacherService iTeacherService;

    @RequestMapping("/DeleteTeachers")//删除教师信息
    public JsonResult<Void> deleteTeachers(String[] uids)
    {
        iTeacherService.delete(uids);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/TeacherById")//搜索教师信息
    public JsonResult<Teacher> searchTeacher(String uid)
    {
        Teacher data=iTeacherService.search(uid);
        return new JsonResult<>(ok,data);
    }

    @GetMapping("/TeacherInfo")//获取所有教师信息
    public JsonResult<List<Teacher>> getAllTeacher(){
        List<Teacher> data = iTeacherService.getTeacherInfo();
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("/ModifyTeacher")//修改教师信息
    public JsonResult<Void> changeInfo(Teacher teacher){
        iTeacherService.updateInfo(teacher);
        return new JsonResult<>(ok);
    }
    @RequestMapping("/InsertTeacher")//新增教师信息
    public JsonResult<Void> insertTeacher(Teacher teacher){
        iTeacherService.insert(teacher);
        return new JsonResult<>(ok);
    }
    @RequestMapping("/SortById")//按工号排序
    public JsonResult<List<Teacher>> sortById(boolean sort){
        List<Teacher> data = iTeacherService.sortById(sort);
        return new JsonResult<>(ok,data);
    }
}
