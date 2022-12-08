package com.cspplatform.controller;

import com.cspplatform.service.IPredictionService;
import com.cspplatform.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("prediction")
public class PredictionController extends BaseController{
    @Autowired
    private IPredictionService predictionService;

    @GetMapping("/SumByGrade")
    public JsonResult<List<Integer>> inquireSumByGrade(String session){
        List<Integer> data = predictionService.inquireSumByGrade(session);
        return new JsonResult<>(ok, data);
    }

    @GetMapping("/SumByType")
    public JsonResult<List<Integer>> inquireSumByType(String session){
        List<Integer> data = predictionService.inquireSumByType(session);
        return new JsonResult<>(ok, data);
    }
}
