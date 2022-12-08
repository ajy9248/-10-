package com.cspplatform.service;

import com.cspplatform.entity.Score;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @InterfaceName : IScoreService  //类名
 * @Description :   //描述
 * @Author : MayBlackCat  //作者
 * @Date: 2022-12-06 00:21  //时间
 */
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

    void insertScores(MultipartFile file,String session);
}
