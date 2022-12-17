package com.cspplatform.controller;

import com.cspplatform.entity.Score;
import com.cspplatform.entity.Student;
import com.cspplatform.service.IScoreService;
import com.cspplatform.service.impl.ScoreServiceImpl;
import com.cspplatform.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("score")
public class ScoreController extends BaseController{

    @Autowired
    IScoreService iscoreService;

    @GetMapping("/ScoreByRange")
    public JsonResult<List<Integer>> inquireScoreByRange(String session){
        List<Integer> data = iscoreService.inquireScoreByRange(session);
        return new JsonResult<>(ok,data);
    }
    @RequestMapping("/InsertScore")
    public JsonResult<Void> insertScore(MultipartFile file,String session){
        iscoreService.insertScores(file,session);
        return new JsonResult<>(ok);
    }

    @GetMapping("/ScoreInfo")
    public JsonResult<List<Score>> getAllScore(){
        List<Score> data=iscoreService.getScoreInfo();
        return new JsonResult<>(ok,data);
    }
    @RequestMapping("/InsertOneScore")//插入单条成绩信息
    public JsonResult<Void> insertOneScore(Score score){
        iscoreService.insertScore(score);
        return new JsonResult<>(ok);
    }

    @GetMapping("/Absence")
    public  JsonResult<List<Student>> inquireAbsence(String session){
        List<Student> data = iscoreService.inquireAbsence(session);
        return new JsonResult<>(ok,data);
    }

    /** 更新allsession，获取所有可选考试场次**/
    @GetMapping("/AllSession")
    public JsonResult<List<String>>getAllSession(){
        List<String> data=iscoreService.getAllSession();
        return new JsonResult<>(ok,data);
    }

    @GetMapping("/highest")
    public JsonResult<Integer> inquireHighest(String session){
        return new JsonResult<>(ok, iscoreService.inquireHighest(session));
    }

    @GetMapping("/average")
    public JsonResult<Integer> inquireAverage(String session){
        return new JsonResult<>(ok, iscoreService.inquireAverage(session));
    }

}
