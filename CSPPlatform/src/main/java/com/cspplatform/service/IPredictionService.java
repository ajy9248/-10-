package com.cspplatform.service;

import com.cspplatform.entity.Prediction;

import java.util.List;

/**
 * @InterfaceName : IPredictionService  //类名
 * @Description :   //描述
 * @Author : MayBlackCat  //作者
 * @Date: 2022-12-06 00:20  //时间
 */
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
}
