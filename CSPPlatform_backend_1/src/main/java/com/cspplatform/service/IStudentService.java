package com.cspplatform.service;

import com.cspplatform.entity.Login;
import com.cspplatform.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface IStudentService {
    /**
     * 用户选中多个学生，进行批量删除
     * @param uids 若干个学生id
     */
    void deleteStudents(String[] uids);

    /**
     * 批量删除学生后，要在login表中删除学生
     * @param uids 若干学生id
     */
    void deleteLogin(String[] uids);

    /**
     * 批量删除学生后，要在prediction_info表中删除学生
     * @param uids 若干学生id
     */
    void deletePrediction(String[] uids);

    /**
     * 批量删除学生后，要在score_info表中删除学生
     * @param uids 若干学生id
     */
    void deleteScore(String[] uids);

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
     * @param student 学生信息
     */
    void updateInfo(Student student);

    /**
     * 用户传入excel表格后，批量添加学生
     * @param file excel文件
     */
    void insertStudents(File file);

    /**
     * 批量加入学生后，要在login表中添加学生
     * @param logins
     */
    void addLogin(Login[] logins);

    Integer inquireFreeChance(Student user);
}
