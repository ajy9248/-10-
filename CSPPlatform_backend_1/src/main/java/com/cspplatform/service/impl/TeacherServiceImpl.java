package com.cspplatform.service.impl;

import com.cspplatform.entity.Teacher;
import com.cspplatform.mapper.TeacherMapper;
import com.cspplatform.service.ITeacherService;
import com.cspplatform.service.ex.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public void deleteTeachers(String[] uids) {
        teacherMapper.deleteTeachers(uids);
        deleteLogin(uids);
    }

    @Override
    public void deleteLogin(String[] uids) {
        teacherMapper.deleteLogin(uids);
    }

    @Override
    public Teacher search(String uid) {
        Teacher result=teacherMapper.infoById(uid);
        if(result!=null)
            return result;
        else
            throw new IdNotFoundException("工号不存在");
    }

    @Override
    public List<Teacher> getTeacherInfo() {
        return teacherMapper.findAllTeacher();
    }

    @Override
    public void updateInfo(Teacher teacher) {
        teacherMapper.updateTeacher(teacher);
    }

    @Override
    public void insert(Teacher teacher) {
        teacherMapper.insertTeacher(teacher);
    }

    @Override
    public List<Teacher> sortById(boolean sort) {
        return teacherMapper.SortById(sort);
    }
}
