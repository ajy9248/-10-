package com.cspplatform.mapper;

import com.cspplatform.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName : ScoreMapper  //类名
 * @Description :   //描述
 * @Author : MayBlackCat  //作者
 * @Date: 2022-12-06 00:15  //时间
 */
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
}
