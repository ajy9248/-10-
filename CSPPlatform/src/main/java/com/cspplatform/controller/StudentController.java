package com.cspplatform.controller;

import com.cspplatform.entity.Login;
import com.cspplatform.entity.Student;
import com.cspplatform.service.IStudentService;
import com.cspplatform.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController{
    @Autowired
    private IStudentService iStudentService;

    @RequestMapping("/DeleteStudents")
    public JsonResult<Void> deleteStudents(String[] uids)
    {
        // 调用业务对象执行查询数据
        iStudentService.delete(uids);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/StudentById")
    public JsonResult<Student> searchStudent(String uid)
    {
        Student data=iStudentService.search(uid);
        return new JsonResult<Student>(ok,data);
    }

    @GetMapping("/StudentInfo")
    public JsonResult<List<Student>> getAllStudent(){
        List<Student> data = iStudentService.getStudentInfo();
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("/ModifyStudent")
    public JsonResult<Void> changeInfo(Student student){
        iStudentService.updateInfo(student);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/AddStudent")
    public JsonResult<Void> insertStudent(MultipartFile file){
        iStudentService.insertStudents(file);
        return new JsonResult<>(ok);
    }

}
