package com.cspplatform.mapper;

import com.cspplatform.entity.Prediction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PredictionMapper {
    /**
     * 根据考试场次查询所有预报名信息
     * @param session 考试场次
     * @return 预报名信息
     */
    public List<Prediction> inquireAllPreBySession(String session);

    /**
     * 根据报名类型返回预报名信息
     * @param type 报名类型
     * @return 预报名信息
     */
    public List<Prediction> inquirePredictionByType(String type);

    /**
     * 根据学号返回预报名信息
     * @param uid 学号
     * @return 预报名信息
     */
    public List<Prediction> inquirePredictionById(String uid);

    /**
     * 返回所有预报名信息
     * @return 预报名信息
     */
    public List<Prediction> inquirePredictions();
}
