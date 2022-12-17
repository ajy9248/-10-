package com.cspplatform.service.impl;

import com.cspplatform.entity.Score;
import com.cspplatform.entity.Student;
import com.cspplatform.mapper.StudentMapper;
import com.cspplatform.service.IStudentService;
import com.cspplatform.service.ex.IdNotFoundException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void delete(String[] uids) {
        studentMapper.deleteStudents(uids);
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
    public void insertStudents(MultipartFile file) {
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
        Student[] students=new Student[columnNum-3];
        for (int i = 3; i < columnNum; i++) {
            //获取每行
            Row row = sheetAt.getRow(i);
            //判断该条数据的uid是否已经在数据库中
            //如果在，则这条数据不会加入
            String uid=row.getCell(11).getStringCellValue();
            Student test=studentMapper.findById(uid);
            if(test!=null)
            {
                continue;
            }
            students[i].setUid(uid);
            students[i].setUname(row.getCell(1).getStringCellValue());
            students[i].setGrade(row.getCell(8).getStringCellValue());
            students[i].setFreeChance(1);
        }
        studentMapper.insertStudents(students);
    }
}
