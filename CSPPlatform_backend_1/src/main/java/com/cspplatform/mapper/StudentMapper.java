package com.cspplatform.mapper;

import com.cspplatform.entity.Login;
import com.cspplatform.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    /**
     * 用户选中多个学生，进行批量删除
     * @param uids 若干学生id
     * @return 匹配行数
     */
    Integer deleteStudents(String[] uids);

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
     * @return Login
     */
    Student infoById(String uid);

    /**
     * 进入学生管理页面时，自动获取全部学生的列表
     * @return 学生数据列表
     */
    List<Student> findAllStudent();

    /**
     * 用户修改学生数据后提交，进行信息更新（uid不能修改）
     * @param student 学生信息
     * @return 受影响的行数
     */
    Integer updateStudent(Student student);

    /**
     * 插入学生信息
     * @param students 学生数组
     */
    void insertStudents(Student[] students);

    /**
     * 批量加入学生后，要在login表中添加学生
     * @param logins
     */
    void addLogin(Login[] logins);

    /**
     * 根据用户id查询免费次数
     * @param user 用户id
     * @return 如果找到则返回数据，反之则返回null
     */
    Integer inquireFreeChance(Student user);

}
