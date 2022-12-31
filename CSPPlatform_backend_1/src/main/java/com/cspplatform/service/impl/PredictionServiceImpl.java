package com.cspplatform.service.impl;

import com.cspplatform.entity.Login;
import com.cspplatform.entity.Prediction;
import com.cspplatform.entity.Student;
import com.cspplatform.entity.Tool;
import com.cspplatform.mapper.PredictionMapper;
import com.cspplatform.service.IPredictionService;
import com.cspplatform.service.ex.CancelPredictionException;
import com.cspplatform.service.ex.StudentSubmitFailure;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class PredictionServiceImpl implements IPredictionService {

    @Autowired
    PredictionMapper predictionMapper;

    @Autowired
    StudentServiceImpl studentService;

    @Override
    public List<Prediction> inquireAllPreBySession(String session) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Prediction> predictions = predictionMapper.inquireAllPreBySession(session);
        for (Prediction prediction : predictions){
            Student student = studentService.search(prediction.getUid());
            prediction.setUname(student.getUname());
            prediction.setGrade(student.getGrade());
            prediction.setTimeString(formatter.format(prediction.getTime()));
        }
        return predictions;
    }

    @Override
    public List<Tool> inquireSumByGrade(String session) {
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
        List<Tool> result = new ArrayList<>();
        Tool tool1 = new Tool();
        Tool tool2 = new Tool();
        Tool tool3 = new Tool();
        Tool tool4 = new Tool();
        tool1.setName(Grade1);
        tool1.setValue(grade1);
        result.add(tool1);

        tool2.setName(Grade2);
        tool2.setValue(grade2);
        result.add(tool2);

        tool3.setName(Grade3);
        tool3.setValue(grade3);
        result.add(tool3);

        tool4.setName(Grade4);
        tool4.setValue(grade4);
        result.add(tool4);
        return result;
    }

    @Override
    public List<Tool> inquireSumByType(String session) {
        List<Prediction> predictions = predictionMapper.inquireAllPreBySession(session);
        int type1 = 0, type2 = 0;
        for (Prediction prediction : predictions){
            if (prediction.getType().equals("免费团报"))
                type1 += 1;
            if (prediction.getType().equals("自费团报"))
                type2 += 1;
        }
        List<Tool> result = new ArrayList<>();
        Tool tool = new Tool();
        Tool tool2 = new Tool();
        tool.setName("免费团报");
        tool.setValue(type1);
        result.add(tool);
        tool2.setName("自费团报");
        tool2.setValue(type2);
        result.add(tool2);
        return result;
    }

    @Override
    public List<Prediction> inquirePredictionByType(String type) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Prediction> predictions = predictionMapper.inquirePredictionByType(type);
        for (Prediction prediction : predictions){
            prediction.setTimeString(formatter.format(prediction.getTime()));
            Student student = studentService.search(prediction.getUid());
            prediction.setUname(student.getUname());
            prediction.setGrade(student.getGrade());
        }
        return predictions;
    }

    @Override
    public List<Prediction> inquirePredictionById(String uid) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Prediction> predictions = predictionMapper.inquirePredictionById(uid);
        for (Prediction prediction : predictions){
            prediction.setTimeString(formatter.format(prediction.getTime()));
            Student student = studentService.search(prediction.getUid());
            prediction.setUname(student.getUname());
            prediction.setGrade(student.getGrade());
        }
        return predictions;
    }

    @Override
    public List<Prediction> inquirePredictions() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Prediction> predictions = predictionMapper.inquirePredictions();
        for (Prediction prediction : predictions){
            prediction.setTimeString(formatter.format(prediction.getTime()));
            Student student = studentService.search(prediction.getUid());
            prediction.setUname(student.getUname());
            prediction.setGrade(student.getGrade());
        }
        return predictions;
    }

    @Override
    public void cancelPrediction(String session, String uid,String current_session) {
        if(current_session.equals(session))
        {
            predictionMapper.cancelPrediction(uid,session);
        }else {
            throw new CancelPredictionException("取消预报名场次错误");
        }
    }

    @Override
    public List<Prediction> inquireMyPrediction(String uid) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Prediction> predictions = predictionMapper.inquireMyPrediction(uid);
        for (Prediction prediction : predictions){
            prediction.setTimeString(formatter.format(prediction.getTime()));
            Student student = studentService.search(prediction.getUid());
            prediction.setUname(student.getUname());
            prediction.setGrade(student.getGrade());
        }
        return predictions;
    }

    @Override
    public void submitPrediction(Prediction prediction,String current_session) {
        if("0".equals(current_session))
            throw new StudentSubmitFailure("非可报名时间报名失败！");
        else
            predictionMapper.submitPrediction(prediction);
    }

    @Override
    public void SubFreeChance(String current_session) {
        predictionMapper.SubFreeChance(current_session);
    }

    @Override
    public void setSessionFlag(String sessionFlag) {
        predictionMapper.setSessionFlag(sessionFlag);
    }

    @Override
    public Login getSessionFlag() {
        return predictionMapper.getSessionFlag();
    }

    @Override
    public void insertPredictions(MultipartFile file) {
        XSSFWorkbook xssfWorkbook = null;

        try {
            InputStream inputStream = file.getInputStream();
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // 获取Excel的第一个sheet
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        //获取行数
        int columnNum = sheetAt.getPhysicalNumberOfRows();
        List<Prediction> list=new ArrayList<>();
        Prediction prediction=new Prediction();
        //Prediction[] predictions=new Prediction[columnNum-2];
        for (int i = 0; i < columnNum; i++) {
            //获取每行
            Row row = sheetAt.getRow(i);
            String uid=row.getCell(1).getStringCellValue();
            System.out.println("uid:"+uid);
            prediction.setUid(uid);
            String type=row.getCell(6).getStringCellValue();
            System.out.println("type:"+type);
            if("自费".equals(type))
                prediction.setType("自费团报");
            else
                prediction.setType("免费团报");
            list.add(prediction);
        }
        predictionMapper.insertPredictions(list);
    }

}
