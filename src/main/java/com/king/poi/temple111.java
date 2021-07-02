package com.king.poi;


import com.king.util.DateUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by ljq on 2019/3/25 10:30
 */
public class temple111 {

    public static void main(String[] args) throws ParseException {


        System.out.println(DateUtil.getDate2("2020-08-21 15:32:53"));
        System.out.println(new Date());

    }

    @Data
    public static class ContractData{
        private int id;
        private String url;
        private BigDecimal jpgPath;
    }

}
