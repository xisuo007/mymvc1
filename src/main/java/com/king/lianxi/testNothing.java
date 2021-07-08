package com.king.lianxi;


import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Date;

/**
 * CoOMMENT  on column finance_ord_fee.
 */

public class testNothing {
    public static void main(String[] args) throws Exception {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "select name from org_info where id = ?";

        String name = jdbcTemplate.queryForObject(sql, String.class, 1);
        System.out.println(name);

        System.out.println(new Date().toString());
        System.out.println((int)(Math.random()*(10-1)+1));

        try (InputStream inputStream = new FileInputStream("2");
             FileReader fileReader = new FileReader("33")){

            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
