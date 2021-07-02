package com.king.controller;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by ljq on 2019/8/7 15:43
 */
@Accessors(chain = true)
@Data
public class UserRet {
    private int id;
    private String name;
    private int age;
    private double price;
    private String HRSB;
}
