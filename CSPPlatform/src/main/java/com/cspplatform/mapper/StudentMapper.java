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
     * @param student
     * @return 受影响的行数
     */
    Integer updateStudent(Student student);

}
