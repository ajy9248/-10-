package com.cspplatform.service;

import com.cspplatform.entity.Prediction;

import java.util.List;

public interface IPredictionService {
    /**
     * 根据考试场次获取全部预报名信息
     * @param session 考试场次
     * @return 预报名信息
     */
    public List<Prediction> inquireAllPreBySession(String session);

    /**
     * 根据考试场次返回各个年级的预报名人数
     * @param session 考试场次
     * @return 预报名人数
     */
    public List<Integer> inquireSumByGrade(String session);

    /**
     * 根据考试场次返回各类预报名人数
     * @param session 考试场次
     * @return 预报名人数
     */
    public List<Integer> inquireSumByType(String session);

    /**
     * 根据报名类型获取全部预报名信息
     * @param type 报名类型
     * @return 预报名信息
     */
    public List<Prediction> inquirePredictionByType(String type);

    /**
     * 根据uid获取全部预报名信息
     * @param uid 报名学生学号
     * @return 预报名信息
     */
    public List<Prediction> inquirePredictionById(String uid);

    /**
     * 返回所有预报名信息
     * @return 预报名信息
     */
    public List<Prediction> inquirePredictions();

    /**
     * 取消预报名
     * @param session 场次
     * @param uid 报名学生学号
     * @param current_session 现在开展的场次
     */
    public void cancelthePrediction(String session,String uid,String current_session);

    /**
     * 返回该生的所有预报名信息
     * @param uid
     * @return 预报名信息
     */
    public List<Prediction> inquireMyPrediction(String uid);

    /**
     * 学生提交预报名信息
     * @param prediction 预报名信息
     */
    public void submitthePrediction(Prediction prediction);
}
