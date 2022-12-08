package com.cspplatform.service.impl;

import com.cspplatform.entity.Student;
import com.cspplatform.mapper.StudentMapper;
import com.cspplatform.service.IStudentService;
import com.cspplatform.service.ex.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void delete(String[] uids) {
        studentMapper.deleteStudents(uids);
    }

    @Override
    public Student search(String uid) {
        Student result=studentMapper.infoById(uid);
        if(result==null)
            throw new IdNotFoundException("用户uid不存在");
        return result;
    }

    @Override
    public List<Student> getStudentInfo() {
        return studentMapper.findAllStudent();
    }

    @Override
    public void updateInfo(Student student) {
        studentMapper.updateStudent(student);
    }
}
