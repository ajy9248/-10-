package com.cspplatform.service;

import com.cspplatform.entity.Teacher;

import java.util.List;

public interface ITeacherService {
    void deleteTeachers(String[] uids);
    void deleteLogin(String[] uids);

    Teacher search(String uid);

    List<Teacher> getTeacherInfo();

    void updateInfo(Teacher teacher);
    void insert(Teacher teacher);

    List<Teacher> sortById(boolean sort);
}
