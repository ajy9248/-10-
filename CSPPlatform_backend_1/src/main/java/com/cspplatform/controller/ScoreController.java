package com.cspplatform.controller;

import com.cspplatform.entity.Score;
import com.cspplatform.entity.Student;
import com.cspplatform.entity.Tool;
import com.cspplatform.service.IScoreService;
import com.cspplatform.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController extends BaseController{

    @Autowired
    private IScoreService iscoreService;

    @RequestMapping("/ScoreByRange")
    public JsonResult<List<Integer>> inquireScoreByRange(String session){
        List<Integer> data = iscoreService.inquireScoreByRange(session);
        return new JsonResult<>(ok,data);
    }
    @RequestMapping("/InsertScore")
    public JsonResult<Void> insertScore(MultipartFile file){
        iscoreService.insertScores(file);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/ScoreInfo")
    public JsonResult<List<Score>> getAllScore(){
        List<Score> data=iscoreService.getScoreInfo();
        return new JsonResult<>(ok,data);
    }
    @RequestMapping("/InsertOneScore")//插入单条成绩信息
    public JsonResult<Void> insertOneScore(Score score){
        iscoreService.insertScore(score);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/Absence")
    public  JsonResult<List<Student>> inquireAbsence(String session){
        List<Student> data = iscoreService.inquireAbsence(session);
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("/highest")
    public JsonResult<Integer> inquireHighest(String session){
        return new JsonResult<>(ok, iscoreService.inquireHighest(session));
    }

    @RequestMapping("/average")
    public JsonResult<Integer> inquireAverage(String session){
        return new JsonResult<>(ok, iscoreService.inquireAverage(session));
    }
    @RequestMapping("/highestAndaverage")
    public JsonResult<Integer[]> inquireHighestAndAverage(String session){
        return new JsonResult<>(ok, iscoreService.inquireHighestAndAverage(session));
    }

    @RequestMapping("/session")
    public JsonResult<List<Tool>>getAllSession(){
        List<Tool> data=iscoreService.getAllSession();
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("/ScoresById")
    public JsonResult<List<Score>> searchScoresById(String uid){
        List<Score> data=iscoreService.searchById(uid);
        return new JsonResult<>(ok,data);
    }
    @RequestMapping("/ScoresBySession")
    public JsonResult<List<Score>> searchScoresBySession(String session){
        List<Score> data=iscoreService.inquireScoreBySession(session);
        return new JsonResult<>(ok,data);
    }
}
