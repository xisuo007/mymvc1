package com.king.lianxi;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @Author ljq
 * @Date 2019/11/5 10:36
 */
public class wen {
    public static void main(String[] args) {

        Test02 test02 = new Test02(1,"3");
        test02.setEnd(null);
        System.out.println(test02.toString());
    }

    @Data
    @AllArgsConstructor
    static class Test02 {
        private Integer id;
        private String end;
    }


}
