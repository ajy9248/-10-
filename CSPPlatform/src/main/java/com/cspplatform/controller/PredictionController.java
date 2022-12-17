package com.cspplatform.controller;

import com.cspplatform.entity.Prediction;
import com.cspplatform.service.IPredictionService;
import com.cspplatform.util.JsonResult;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/prediction")
public class PredictionController extends BaseController{
    @Autowired
    private IPredictionService predictionService;

    @GetMapping("/SumByGrade")
    public JsonResult<List<Integer>> inquireSumByGrade(String session){
        List<Integer> data = predictionService.inquireSumByGrade(session);
        return new JsonResult<>(ok, data);
    }

    @GetMapping("/PredictionByType")
    public JsonResult<List<Prediction>> inquirePredictionByType(String type){
        List<Prediction> data = predictionService.inquirePredictionByType(type);
        return new JsonResult<>(ok, data);
    }

    @GetMapping("/PredictionById")
    public JsonResult<List<Prediction>> inquirePredictionById(String uid){
        List<Prediction> data = predictionService.inquirePredictionById(uid);
        return new JsonResult<>(ok, data);
    }

    @GetMapping("/PredictionInfo")
    public JsonResult<List<Prediction>> inquirePredictions(){
        List<Prediction> data = predictionService.inquirePredictions();
        return new JsonResult<>(ok, data);
    }

    @RequestMapping("/ModifyPrediction")
    public JsonResult<Void> submitSession(HttpSession ssession, String session){
        ssession.setAttribute("session",session);
        System.out.println(getSessionFromSession(ssession));
        return new JsonResult<>(ok);
    }

    @GetMapping("/PredictionSession")
    public JsonResult<String> inquireSession(HttpSession ssession){
        String data = getSessionFromSession(ssession);
        System.out.println(getSessionFromSession(ssession));
        return new JsonResult<String>(ok,data);
    }

    @RequestMapping("/CancelPrediction")
    public JsonResult<Void> cancel(HttpSession ssession,String session,String uid){
        String current_session=getSessionFromSession(ssession);
        predictionService.cancelthePrediction(session,uid,current_session);
        return new JsonResult<>(ok);
    }

    @GetMapping("/MyPrediction")
    public JsonResult<List<Prediction>> inquiremypredictions(String uid){
        List<Prediction> data = predictionService.inquireMyPrediction(uid);
        return new JsonResult<>(ok, data);
    }

    @RequestMapping("/AddPrediction")
    public JsonResult<Void> Submit(Prediction prediction){
        predictionService.submitthePrediction(prediction);
        return new JsonResult<>(ok);
    }
}
