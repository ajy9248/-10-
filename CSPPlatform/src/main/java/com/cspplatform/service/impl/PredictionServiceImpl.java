package com.cspplatform.service.impl;

import com.cspplatform.entity.Prediction;
import com.cspplatform.mapper.PredictionMapper;
import com.cspplatform.service.IPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class PredictionServiceImpl implements IPredictionService {

    @Autowired
    PredictionMapper predictionMapper;

    @Autowired
    StudentServiceImpl studentService;

    @Override
    public List<Prediction> inquireAllPreBySession(String session) {
        return predictionMapper.inquireAllPreBySession(session);
    }

    @Override
    public List<Integer> inquireSumByGrade(String session) {
        List<Prediction> predictions = predictionMapper.inquireAllPreBySession(session);
        int grade1 = 0, grade2 = 0,  grade3 = 0, grade4 = 0;
        String Grade1 = "级", Grade2 = "级", Grade3 = "级", Grade4 = "级";
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        int year = calendar.get(Calendar.YEAR);
        // 获取当前月
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 7){
            year--;
        }
        Grade4 = year + Grade4;
        year--;
        Grade3 = year + Grade3;
        year--;
        Grade2 = year + Grade2;
        year--;
        Grade1 = year + Grade1;

        for (Prediction prediction : predictions){
            if (studentService.search(prediction.getUid()).getGrade().equals(Grade1))
                grade1 += 1;
            if (studentService.search(prediction.getUid()).getGrade().equals(Grade2))
                grade2 += 1;
            if (studentService.search(prediction.getUid()).getGrade().equals(Grade3))
                grade3 += 1;
            if (studentService.search(prediction.getUid()).getGrade().equals(Grade4))
                grade4 += 1;
        }
        List<Integer> result = new ArrayList<>();
        result.add(grade1);
        result.add(grade2);
        result.add(grade3);
        result.add(grade4);
        return result;
    }

    @Override
    public List<Integer> inquireSumByType(String session) {
        List<Prediction> predictions = predictionMapper.inquireAllPreBySession(session);
        int type1 = 0, type2 = 0;
        for (Prediction prediction : predictions){
            if (studentService.search(prediction.getUid()).getGrade().equals("免费团报"))
                type1 += 1;
            if (studentService.search(prediction.getUid()).getGrade().equals("自费团报"))
                type2 += 1;
        }
        List<Integer> result = new ArrayList<>();
        result.add(type1);
        result.add(type2);
        return result;
    }

    @Override
    public List<Prediction> inquirePredictionByType(String type) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Prediction> predictions = predictionMapper.inquirePredictionByType(type);
        for (Prediction prediction : predictions){
            prediction.setTimeString(formatter.format(prediction.getTime()));
        }
        return predictions;
    }

    @Override
    public List<Prediction> inquirePredictionById(String uid) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Prediction> predictions = predictionMapper.inquirePredictionById(uid);
        for (Prediction prediction : predictions){
            prediction.setTimeString(formatter.format(prediction.getTime()));
        }
        return predictions;
    }

    @Override
    public List<Prediction> inquirePredictions() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Prediction> predictions = predictionMapper.inquirePredictions();
        for (Prediction prediction : predictions){
            prediction.setTimeString(formatter.format(prediction.getTime()));
        }
        return predictions;
    }
}
