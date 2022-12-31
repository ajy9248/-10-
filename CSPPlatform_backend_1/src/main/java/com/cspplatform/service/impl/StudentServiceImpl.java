package com.cspplatform.service.impl;

import com.cspplatform.entity.Login;
import com.cspplatform.entity.Student;
import com.cspplatform.mapper.StudentMapper;
import com.cspplatform.service.IStudentService;
import com.cspplatform.service.ex.IdNotFoundException;
import com.cspplatform.service.ex.StudentsRepeatedImport;
import com.cspplatform.service.ex.SuffixErrorException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void deleteStudents(String[] uids) {
        studentMapper.deleteStudents(uids);
        deleteLogin(uids);
        deletePrediction(uids);
        deleteScore(uids);
    }

    @Override
    public void deleteLogin(String[] uids) {
        studentMapper.deleteLogin(uids);
    }

    @Override
    public void deletePrediction(String[] uids) {
        studentMapper.deletePrediction(uids);
    }

    @Override
    public void deleteScore(String[] uids) {
        studentMapper.deleteScore(uids);
    }

    @Override
    public Student search(String uid) {
        Student result=studentMapper.infoById(uid);
        if(result==null)
            throw new IdNotFoundException("用户uid不存在");
        return result;
    }

    @Override
    public List<Student> getStudentInfo() {
        return studentMapper.findAllStudent();
    }

    @Override
    public void updateInfo(Student student) {
        studentMapper.updateStudent(student);
    }

    @Override
    public void insertStudents(File file) {
        Workbook workbook = null;
        String str=file.getName();
        String suffix=str.substring(str.lastIndexOf(".")+1);
        System.out.println(suffix);
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
        System.out.println(columnNum);
        Student[] students=new Student[columnNum-1];
        Login[] logins=new Login[columnNum-1];
        for (int i =1; i < columnNum; i++) {
            //获取每行
            students[i-1]=new Student();
            logins[i-1]=new Login();
            Row row = sheetAt.getRow(i);
            //students[i-1].setUid(String.valueOf((long) row.getCell(0).getNumericCellValue()));
            students[i-1].setUid(row.getCell(0).getStringCellValue());
            students[i-1].setUname(row.getCell(1).getStringCellValue());
            students[i-1].setGrade(row.getCell(2).getStringCellValue());
            students[i-1].setFreechance(1);
            //System.out.println(students[i-1].getUid()+students[i-1].getUname()+students[i-1].getGrade()+students[i-1].getFreechance());
            //logins[i-1].setUid(String.valueOf((long) row.getCell(0).getNumericCellValue()));
            logins[i-1].setUid(row.getCell(0).getStringCellValue());
            logins[i-1].setPwd("123456");
            logins[i-1].setType("学生");
            logins[i-1].setSessionFlag("0");
        }
        try {
            studentMapper.insertStudents(students);
            addLogin(logins);
        }catch (org.springframework.dao.DuplicateKeyException e){
            throw new StudentsRepeatedImport("导入数据重复");
        }
    }

    @Override
    public void addLogin(Login[] logins) {
        studentMapper.addLogin(logins);
    }

    @Override
    public Integer inquireFreeChance(Student user) {
        return studentMapper.inquireFreeChance(user);
    }
}
