package com.king.poi.hutoolPoi;

import lombok.Data;

import java.util.Date;

/**
 * Created by ljq on 2019/3/25 13:14
 */
@Data
public class User {
    private String name;
    private int age;
    private double score;
    private boolean isPass;
    private Date examDate;
}
