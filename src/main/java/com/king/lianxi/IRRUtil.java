package com.king.lianxi;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class IRRUtil {

    /**
     * 计算IRR
     * @param list 现金流
     * @param guess 预估值
     */
    public static Double irr(List<Transaction> list, double guess) {
        Double irrResult = 0.0D / 0.0D;
        if (!list.isEmpty()) {
            int maxIterationCount = 20;
            double absoluteAccuracy = 1.0E-7D;
            double fValue, fDerivative, x1;
            for (int i = 0; i < maxIterationCount; ++i) {
                fValue = 0.0D;
                fDerivative = 0.0D;
                for (int k = 0; k < list.size(); ++k) {
                    fValue += list.get(k).getAmount() / Math.pow(1.0D + guess, (double) k);
                    fDerivative += (double) (-k) * list.get(k).getAmount() / Math.pow(1.0D + guess, (double) (k + 1));
                }
                x1 = guess - fValue / fDerivative;
                if (Math.abs(x1 - guess) <= absoluteAccuracy) {
                    irrResult = x1;
                    break;
                }
                guess = x1;
            }
        }
        return irrResult;
    }
    
}