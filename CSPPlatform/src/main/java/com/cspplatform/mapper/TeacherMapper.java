package com.cspplatform.mapper;

import com.cspplatform.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    /**
     * 批量删除选中教师
     * @param uids 选中教师的id
     */
    void deleteTeachers(String[] uids);

    /**
     * 搜索工号对应的教师信息
     * @param uid 教师id
     * @return 工号对应教师信息
     */
    Teacher infoById(String uid);

    /**
     * 获取所有教师信息
     * @return 所有教师信息
     */
    List<Teacher> findAllTeacher();

    /**
     * 修改教师信息
     * @param teacher   修改的教师对象
     */
    void updateTeacher(Teacher teacher);
    /**
     * 批量删除选中教师
     * @param teacher   插入的教师信息
     */
    void insertTeacher(Teacher teacher);
    /**
     * 根据工号排序
     * @param sort true:降序 false:升序
     * @return 所有教师信息
     */
    List<Teacher> SortById(Boolean sort);
}
