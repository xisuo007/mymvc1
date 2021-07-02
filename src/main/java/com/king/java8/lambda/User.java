package com.king.java8.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by ljq on 2019/7/24 9:17
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class User {
    private int id;
    private String name;
    private int age;
    private double price;


}
