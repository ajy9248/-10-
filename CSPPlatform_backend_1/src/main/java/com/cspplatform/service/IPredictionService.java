package com.cspplatform.service;

import com.cspplatform.entity.Login;
import com.cspplatform.entity.Prediction;
import com.cspplatform.entity.Tool;
import org.springframework.web.multipart.MultipartFile;

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
    public List<Tool> inquireSumByGrade(String session);

    /**
     * 根据考试场次返回各类预报名人数
     * @param session 考试场次
     * @return 预报名人数
     */
    public List<Tool> inquireSumByType(String session);

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
    public void cancelPrediction(String session,String uid,String current_session);

    /**
     * 返回该生的所有预报名信息
     * @param uid 学生id
     * @return 预报名信息
     */
    public List<Prediction> inquireMyPrediction(String uid);

    /**
     * 学生提交预报名信息
     * @param prediction 预报名信息
     */
    public void submitPrediction(Prediction prediction,String current_session);

    /**
     * 免费次数-1
     * @param current_session 当前场次
     */
    public void SubFreeChance(String current_session);

    /**
     * 在login表中更新当前session
     * @param sessionFlag 当前session
     */
    public void setSessionFlag(String sessionFlag);

    /**
     * 从login表中获取存有当前session的Login
     * @return Login类型的对象
     */
    public Login getSessionFlag();

    /**
     * 用户传入excel表格后，批量添加
     * @param file excel文件
     */
    void insertPredictions(MultipartFile file);
}
