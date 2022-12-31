package com.cspplatform.mapper;

import com.cspplatform.entity.Login;
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

    /**
     * 取消预报名
     */
    public void cancelPrediction(String uid,String session);

    /**
     * 返回该生的所有预报名信息
     * @param uid 学生id
     * @return 所有预报名信息
     */
    List<Prediction> inquireMyPrediction(String uid);

    /**
     * 提交预报名信息
     * @param prediction 预报名信息
     */
    void submitPrediction(Prediction prediction);

    /**
     * 免费次数-1
     * @param current_session 当前场次
     */
    void SubFreeChance(String current_session);

    /**
     * 在login表中更新当前session
     * @param sessionFlag 当前session
     */
    void setSessionFlag(String sessionFlag);

    /**
     * 从login表中获取存有当前session的Login
     * @return Login类型的对象
     */
    Login getSessionFlag();

    void insertPredictions(List<Prediction> list);
}
