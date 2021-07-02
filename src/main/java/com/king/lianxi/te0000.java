package com.king.lianxi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.king.java8.lambda.User;
import com.king.lianxi.ret.BaseResult;
import com.king.lianxi.ret.DefaultResult;
import com.king.lomboktest.CarBuilder;
import com.king.util.DateUtil;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @Author ljq
 * @Date 2019/10/25 10:14
 */
public class te0000 {

    public static void main(String[] args){
        //TestThread t1 = new TestThread();
        //TestThreadManage manage = new TestThreadManage();
        //manage.add("123",t1);
        //t1.start();

        //CountdownDTO countdownDTO = new CountdownDTO();
        //countdownDTO.setCountdownContent("消息内容1111");
        //countdownDTO.setCountdownNo("a8c51c40-ed03-4234-ac95-7989d8854406");
        //countdownDTO.setOperationType("ADD");
        //countdownDTO.setCountdownTime(1587866899147L);
        //String s = JSON.toJSONString(countdownDTO);
        //System.out.println(s);
        //
        //CountdownDTO countdownDTO1 = JSONObject.parseObject(s, CountdownDTO.class);
        //System.out.println(countdownDTO1.toString());
        //System.out.println(s);

        System.out.println(System.currentTimeMillis()+30000);

        HashMap<String, String> map = new HashMap<>();
        map.put("lisi","1212");
        String s = JSON.toJSONString(map);
        System.out.println(s);

        /*TestJsonList ret = new TestJsonList();
        ret.setName("zhangsan");
        TestJsonList.ParamData paramData = new TestJsonList.ParamData();
        paramData.setKey("key1");
        paramData.setValue("value1");
        paramData.setDesc("描述");
        List<TestJsonList.ParamData> paramList = new ArrayList<>();
        paramList.add(paramData);
        ret.setParamList(paramList);

        String jsonString = JSON.toJSONString(paramList);

        System.out.println(jsonString);


        List<TestJsonList.ParamData> list = JSON.parseArray(jsonString, TestJsonList.ParamData.class);
        System.out.println(list.toString());
        */

        //DefaultResult result1 = new DefaultResult();
        //if (DefaultResult.class.isInstance(result1)) {
        //    System.out.println("----------true");
        //}
        //
        //
        //Map<String, BaseResult> baseMap = new HashMap<>();
        //baseMap.put(new DefaultResult<T>().getClass().getName(),new DefaultResult<>());
        //BaseResult result = baseMap.get("com.king.lianxi.ret.DefaultResult");
        //System.out.println(result.getClass().getName());



    }



    static class TestThread extends Thread{
        String name;
        public TestThread(String name){
            this.name = name;
        }
        @Override
        public void run() {
            TestThreadManage manage = new TestThreadManage();
            manage.remove("123");
            try {
                System.out.println("线程休眠："+name);
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class TestThreadManage{
        Map<String,Thread> map = new ConcurrentHashMap<>();
        public void add(String countdownNo,Thread thread){
            map.put(countdownNo,thread);
        }

        public void remove(String countdown){
            Thread thread = map.get(countdown);
            thread.interrupt();
        }
    }

    @Data
    public static class CountdownDTO {

        /**
         * 倒计时内容
         */
        private String countdownContent;

        /**
         * 倒计时编码
         */
        private String countdownNo;

        /**
         * 倒计时结束时间
         */
        private long countdownTime;

        /**
         * 操作类型:新增、删除
         */
        private String operationType;
    }


    @Data
    public static class TestJsonList{
        private String name;
        /**
         * 参数json列表
         */
        private List<ParamData> paramList;

        @Data
        public static class ParamData{
            private String key;
            private String value;
            private String desc;
        }
    }
}


