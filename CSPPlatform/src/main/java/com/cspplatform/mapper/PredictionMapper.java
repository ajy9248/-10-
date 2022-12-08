package com.cspplatform.mapper;

import com.cspplatform.entity.Prediction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName : PredictionMapper  //类名
 * @Description :   //描述
 * @Author : MayBlackCat  //作者
 * @Date: 2022-12-06 00:14  //时间
 */
@Mapper
public interface PredictionMapper {
    /**
     * 根据考试场次查询所有预报名信息
     * @param session 考试场次
     * @return 预报名信息
     */
    public List<Prediction> inquireAllPreBySession(String session);
}
