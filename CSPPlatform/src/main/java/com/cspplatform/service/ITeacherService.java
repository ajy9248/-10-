package com.cspplatform.service;

import com.cspplatform.entity.Teacher;

import java.util.List;

public interface ITeacherService {
    void delete(String[] uids);

    Teacher search(String uid);

    List<Teacher> getTeacherInfo();

    void updateInfo(Teacher teacher);
    void insert(Teacher teacher);

    List<Teacher> sortById(boolean sort);
}
