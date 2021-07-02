package com.king.lianxi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljq on 2020-12-7 17:40
 */
public class IrrTest {

    public static void main(String[] args){
        BigDecimal loanAmount = new BigDecimal("95000");
        Integer periods = 36;//期数
        BigDecimal standardRate = new BigDecimal("7.78");
        BigDecimal multiply = loanAmount.multiply(new BigDecimal(1).add(standardRate.divide(new BigDecimal(100))));
        System.out.println(multiply.toString());
        BigDecimal divide = multiply.divide(new BigDecimal(periods + ""), 2, BigDecimal.ROUND_UP);
        System.out.println(divide.toString());
        BigDecimal monthValue = loanAmount.multiply(new BigDecimal(1).add(standardRate.divide(new BigDecimal(100)))).divide(new BigDecimal(periods+""),2,BigDecimal.ROUND_UP);
        List<Transaction> list = new ArrayList<>();


        list.add(new Transaction(loanAmount.divide(new BigDecimal("-1")).doubleValue()));
        for (Integer i = 0; i < periods; i++) {
            list.add(new Transaction(monthValue.doubleValue()));
        }
        System.out.println(list.size());

        Double irr = IRRUtil.irr(list, 0D);
        System.out.println(irr);

        BigDecimal yearValue = new BigDecimal(irr.toString()).multiply(new BigDecimal(12)).multiply(new BigDecimal(100)).divide(new BigDecimal(1), 2, BigDecimal.ROUND_UP);
        System.out.println(new BigDecimal(irr.toString()).toString());
        BigDecimal multiply1 = new BigDecimal(irr.toString()).multiply(new BigDecimal(12));
        System.out.println(multiply1.toString());

        System.out.println(yearValue.toString());
    }
}



