package com.cspplatform.service;

import com.cspplatform.service.impl.PredictionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//表示启动这个单元测试类
@RunWith(SpringRunner.class)
public class PredictionServiceTest {
    @Autowired
    PredictionServiceImpl predictionService;

    @Test
    public void inquireAllPreBySession(){
        System.out.println(predictionService.inquireAllPreBySession("21"));
    }
}
