package com.cspplatform.controller;

import com.cspplatform.entity.Score;
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
        return new JsonResult(ok);
    }
}
