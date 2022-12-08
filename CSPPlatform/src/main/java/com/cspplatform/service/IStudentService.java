package com.cspplatform.service;

import com.cspplatform.entity.Student;

import java.util.List;

public interface IStudentService {
    /**
     * 用户选中多个学生，进行批量删除
     * @param uids 若干个学生id
     */
    void delete(String[] uids);

    /**
     * 用户输入学生学号，进行信息查询
     * @param uid 学生id
     * @return  Student
     */
    Student search(String uid);

    /**
     * 查询所有学生
     * @return 学生数据列表
     */
    List<Student> getStudentInfo();

    /**
     * 用户修改学生数据后提交，进行信息更新
     * @param student
     */
    void updateInfo(Student student);

}
