package com.cspplatform.mapper;

import com.cspplatform.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * 根据考试场次查询成绩信息
     * @param session 考试场次
     * @return 成绩
     */
    List<Score> inquireScoreBySession(String session);
    /**
     * 插入成绩信息
     * @param scores 成绩数组
     */
    void insertScores(Score[] scores);
    /**
     * 获取所有成绩信息
     * @return 所有成绩信息
     */
    List<Score> findAllScore();
    /**
     * 插入成绩信息
     * @param score 成绩对象
     */
    void insertScore(Score score);

    /**
     * 根据考试场次按分数从高到低获取成绩信息
     * @param session 考试场次
     * @return 降序返回成绩信息
     */
    List<Score> inquireScoreOrder(String session);

    /**
     * 获取所有可选场次
     * @return 所有可选场次
     */
    List<String> findAllSession();

    /**
     * 根据学号查询考试成绩
     * @param uid 考生学号
     * @return 该考生的所有考试成绩
     */
    List<Score> inquireScoreById(String uid);
}
