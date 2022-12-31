package com.cspplatform.service.impl;

import com.cspplatform.entity.Prediction;
import com.cspplatform.entity.Score;
import com.cspplatform.entity.Student;
import com.cspplatform.entity.Tool;
import com.cspplatform.mapper.ScoreMapper;
import com.cspplatform.service.IScoreService;
import com.cspplatform.service.ex.ScoresRepeatedImport;
import com.cspplatform.service.ex.SuffixErrorException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
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
        List<Score> scores = scoreMapper.inquireScoreBySession(session);
        for (Score l:scores
        ) {
            Student student = studentService.search(l.getUid());
            l.setGrade(student.getGrade());
            l.setUname(student.getUname());
        }
        return scores;

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
    public void insertScores(MultipartFile multipartFile) {
        //转换为file

        Workbook workbook = null;
        String str=multipartFile.getOriginalFilename();
        String session=str.substring(0,str.lastIndexOf("."));
        String suffix=str.substring(str.lastIndexOf(".")+1);
        File file;
        try {
            InputStream inputStream=multipartFile.getInputStream();
            file=File.createTempFile(str,suffix);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!suffix.equals("xlsx"))
            throw new SuffixErrorException("格式错误");
        try {
            InputStream inputStream = new FileInputStream(file);
            workbook = new  XSSFWorkbook(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // 获取Excel的第一个sheet
        Sheet sheetAt = workbook.getSheetAt(0);
        //获取行数
        int columnNum = sheetAt.getPhysicalNumberOfRows();
        Score[]scores=new Score[columnNum-1];
        for (int i = 1; i < columnNum; i++) {
            //获取每行
            scores[i-1]=new Score();
            Row row = sheetAt.getRow(i);
            //对于常规格式
            //scores[i-1].setUid(String.valueOf((int) row.getCell(0).getNumericCellValue()));
            //对于文本格式
            scores[i-1].setUid(row.getCell(0).getStringCellValue());
            scores[i-1].setScore((int) row.getCell(1).getNumericCellValue());
            scores[i-1].setSession(session);
        }
        try {
            scoreMapper.insertScores(scores);
        }catch (org.springframework.dao.DuplicateKeyException e){
            throw new ScoresRepeatedImport("导入数据重复");
        }
    }

    @Override
    public List<Score> getScoreInfo() {
        List<Score> scores = scoreMapper.findAllScore();
        for (Score l:scores
        ) {
            Student student = studentService.search(l.getUid());
            l.setGrade(student.getGrade());
            l.setUname(student.getUname());
        }
        return scores;
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
    @Override
    public Integer[] inquireHighestAndAverage(String session) {
        Integer[] result = new Integer[2];
        result[0] = scoreMapper.inquireScoreOrder(session).get(0).getScore();
        List<Score> scores = scoreMapper.inquireScoreBySession(session);
        int sum = 0;
        for(Score score : scores){
            sum += score.getScore();
        }
        result[1] = sum / scores.size();

        return result;
    }

    @Override
    public List<Tool> getAllSession() {
        List<String> allSession=scoreMapper.findAllSession();
        List<Tool> list=new ArrayList<>();
        for (String str:allSession
        ) {
            Tool t = new Tool();
            t.setName("第"+str+"次");
            t.setValue(Integer.valueOf(str));
            list.add(t);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<Score> searchById(String uid) {
        List<Score> scores = scoreMapper.inquireScoreById(uid);
        Student student = studentService.search(uid);
        for (Score l:scores
        ) {
            l.setGrade(student.getGrade());
            l.setUname(student.getUname());
        }
        return scores;
    }

}
