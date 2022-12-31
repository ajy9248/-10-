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
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController{
    @Autowired
    private IStudentService iStudentService;

    @RequestMapping("/DeleteStudents")
    public JsonResult<Void> deleteStudents(String[] uids)
    {
        // 调用业务对象执行查询数据
        iStudentService.deleteStudents(uids);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/StudentById")
    public JsonResult<Student> searchStudent(String uid)
    {
        Student data=iStudentService.search(uid);
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("/StudentInfo")
    public JsonResult<List<Student>> getAllStudent(){
        List<Student> data = iStudentService.getStudentInfo();
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("/ModifyStudent")
    public JsonResult<Void> changeInfo(String uid,String uname,String grade,Integer freeChance){
        Student student=new Student(uid,uname,grade,freeChance);
        System.out.println(uid);
        System.out.println(uname);
        System.out.println(grade);
        System.out.println(freeChance);
        iStudentService.updateInfo(student);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/AddStudent")
    public JsonResult<Void> insertStudent(MultipartFile file) throws IOException {
       //File f = new File("C:\\Users\\12345\\Desktop\\1.xlsx");
        File f=new File("src/1.xlsx");
        if(!f.exists())
            f.createNewFile();
        org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(),f);
        iStudentService.insertStudents(f);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/FreeChance")
    public JsonResult<Integer> inquireFreeChance(Student user){
        Integer data = iStudentService.inquireFreeChance(user);
        return new JsonResult<>(ok,data);
    }
}
