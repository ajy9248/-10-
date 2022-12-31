package com.cspplatform.controller;

import com.cspplatform.entity.Prediction;
import com.cspplatform.entity.Tool;
import com.cspplatform.service.IPredictionService;
import com.cspplatform.util.JsonResult;
import javafx.scene.input.DataFormat;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.ToLongBiFunction;

@RestController
@RequestMapping("/prediction")
public class PredictionController extends BaseController{
    @Autowired
    private IPredictionService predictionService;

    @RequestMapping("/SumByGrade")
    public JsonResult<List<Tool>> inquireSumByGrade(String session){
        List<Tool> data = predictionService.inquireSumByGrade(session);
        return new JsonResult<>(ok, data);
    }


    @RequestMapping("/SumByType")
    public JsonResult<List<Tool>> inquireSumByType(String session){
        List<Tool> data = predictionService.inquireSumByType(session);
        return new JsonResult<>(ok, data);
    }

//    @RequestMapping("/PredictionByType")
//    public JsonResult<List<Prediction>> inquirePredictionByType(String type){
//        List<Prediction> data = predictionService.inquirePredictionByType(type);
//        return new JsonResult<>(ok, data);
//    }

    @RequestMapping("/PredictionById")
    public JsonResult<List<Prediction>> inquirePredictionById(String uid){
        List<Prediction> data = predictionService.inquirePredictionById(uid);
        return new JsonResult<>(ok, data);
    }

    @RequestMapping("/PredictionInfo")
    public JsonResult<List<Prediction>> inquirePredictions(){
        List<Prediction> data = predictionService.inquirePredictions();
        return new JsonResult<>(ok, data);
    }

    @RequestMapping("/ModifyPrediction")
    public JsonResult<Void> submitSession(String session){
        predictionService.setSessionFlag(session);
        //ssession.setAttribute("session",session);
        //System.out.println(getSessionFromSession(ssession));
        return new JsonResult<>(ok);
    }

    @RequestMapping("/PredictionSession")
    public JsonResult<String> inquireSession(){
        String data = predictionService.getSessionFlag().getSessionFlag();
        System.out.println(data);
        return new JsonResult<String>(ok,data);
    }

    @RequestMapping("/CancelPrediction")
    public JsonResult<Void> cancel(String session,String uid){
        String current_session=predictionService.getSessionFlag().getSessionFlag();
        predictionService.cancelPrediction(session,uid,current_session);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/MyPrediction")
    public JsonResult<List<Prediction>> inquireMyPrediction(String uid){
        List<Prediction> data = predictionService.inquireMyPrediction(uid);
        return new JsonResult<>(ok, data);
    }

    @RequestMapping("/AddPrediction")
    public JsonResult<Void> Submit(String uid,String type){
        Prediction prediction=new Prediction();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        prediction.setUid(uid);
        prediction.setType(type);
        prediction.setTime(date);
        prediction.setSession(predictionService.getSessionFlag().getSessionFlag());
        String current_session=predictionService.getSessionFlag().getSessionFlag();
        predictionService.submitPrediction(prediction,current_session);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/SubFreeChance")
    public JsonResult<Void> subFreechance(){
        String current_session=predictionService.getSessionFlag().getSessionFlag();
        predictionService.setSessionFlag("0");
        predictionService.SubFreeChance(current_session);
        return new JsonResult<>(ok);
    }

    @RequestMapping("/InsertPredictions")
    public JsonResult<Void> insertPredictions(MultipartFile file){
        predictionService.insertPredictions(file);
        System.out.println("接受到");
        return new JsonResult<>(ok);
    }
}
