package com.cspplatform.service;

import com.cspplatform.entity.Score;
import com.cspplatform.entity.Student;
import com.cspplatform.entity.Tool;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IScoreService {
    /**
     * 根据考试场次查询成绩信息
     * @param session 考试场次
     * @return 成绩
     */
    List<Score> inquireScoreBySession(String session);

    /**
     * 根据考试场次查询所有范围成绩的人数
     * @param session 考试场次
     * @return 所有范围成绩的人数
     */
    List<Integer> inquireScoreByRange(String session);

    void insertScores(MultipartFile file);

    /**
     * 获取所有成绩
     * @return 返回装载Score对象的容器
     */
    List<Score> getScoreInfo();

    /**
     * 插入单条成绩信息
     * @param score 需要插入的成绩对象
     */
    void insertScore(Score score);

    /**
     * 根据考试场次查询缺考学生信息
     * @param session 考试场次
     * @return 缺考学生信息
     */
    List<Student> inquireAbsence(String session);

    /**
     * 获取某场考试的最高分
     * @param session 考试场次
     * @return 最高分
     */
    Integer inquireHighest(String session);

    /**
     * 获取某场考试的平均分
     * @param session 考试场次
     * @return 平均分
     */
    Integer inquireAverage(String session);

    /**
     * 获取某场考试的最高分和均分
     * @param session 考试场次
     * @return 第一个值为最高分，第二个为均分
     */
    Integer[] inquireHighestAndAverage(String session);

    /**
     * 获取所有可选场次
     * @return 返回一个装载场次种类的容器
     */
    List<Tool> getAllSession();

    /**
     * 获取某学生的所有考试成绩
     * @param uid 学生学号
     * @return 该学生所有考试成绩
     */
    List<Score> searchById(String uid);

}
