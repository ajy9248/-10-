package com.cspplatform.service.impl;

import com.cspplatform.entity.Prediction;
import com.cspplatform.entity.Score;
import com.cspplatform.entity.Student;
import com.cspplatform.mapper.ScoreMapper;
import com.cspplatform.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreServiceImpl implements IScoreService {

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    PredictionServiceImpl predictionService;

    @Autowired
    StudentServiceImpl studentService;

    @Override
    public List<Score> inquireScoreBySession(String session) {
        return scoreMapper.inquireScoreBySession(session);
    }

    @Override
    public List<Integer> inquireScoreByRange(String session) {
        List<Score> scores = this.inquireScoreBySession(session);
        List<Integer> results = new ArrayList<>();
        int s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0;
        for (Score score : scores){
            Integer score_info =score.getScore();
            if (score_info >= 0 && score_info <= 100)
                s1 += 1;
            if (score_info > 100 && score_info <= 200)
                s2 += 1;
            if (score_info > 200 && score_info <= 300)
                s3 += 1;
            if (score_info > 300 && score_info <= 400)
                s4 += 1;
            if (score_info > 400 && score_info <= 500)
                s5 += 1;
        }
        results.add(s1);
        results.add(s2);
        results.add(s3);
        results.add(s4);
        results.add(s5);
        return results;
    }

    @Override
    public void insertScores(MultipartFile file,String session){
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
        Score[]scores=new Score[columnNum-3];
        for (int i = 0; i < columnNum; i++) {
            //获取每行
            Row row = sheetAt.getRow(i);
            if(i>2){
                scores[i-3].setUid(row.getCell(7).getStringCellValue());
                scores[i-3].setScore(Integer.valueOf(row.getCell(10).getStringCellValue()));
                scores[i-3].setSession(session);
            }
        }
        scoreMapper.insertScores(scores);
    }

    @Override
    public List<Score> getScoreInfo() {
        return scoreMapper.findAllScore();
    }

    @Override
    public void insertScore(Score score) {
        scoreMapper.insertScore(score);
    }

    @Override
    public List<Student> inquireAbsence(String session) {
        List<Score> scores = scoreMapper.inquireScoreBySession(session);
        List<Prediction> predictions = predictionService.inquireAllPreBySession(session);
        List<Student> students = new ArrayList<>();
        boolean flag = false;
        for (Prediction prediction : predictions){
            for (Score score : scores){
                if (score.getUid().equals(prediction.getUid())){
                    flag = true;
                    break;
                }else {
                    flag = false;
                }
            }
            if (!flag){
                students.add(studentService.search(prediction.getUid()));
            }
        }
        return students;
    }

    @Override
    public List<String> getAllSession() {
        return scoreMapper.findAllSession();
    }

    @Override
    public Integer inquireHighest(String session) {
        return scoreMapper.inquireScoreOrder(session).get(0).getScore();
    }

    @Override
    public Integer inquireAverage(String session) {
        List<Score> scores = scoreMapper.inquireScoreBySession(session);
        int sum = 0;
        for(Score score : scores){
            sum += score.getScore();
        }
        return sum / scores.size();
    }
}
