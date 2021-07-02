package com.king.lianxi;

import com.king.lomboktest.CarChain;
import com.king.lomboktest.CarExt;
import com.king.util.dtoutil.DTOConvert;



/**
 * @Author ljq
 * @Date 2019/10/23 14:28
 */
public class DTOConvertTest {
    public static void main(String[] args){

        DTOConvertTest test = new DTOConvertTest();
        CarExt ext = new CarExt().setYear("2018");
        ext.setColor("红色");
        ext.setName("奥迪A4L");
        ext.setSize("2.0L");
        test.test(ext);
    }

    public void test(CarExt ext){
        CarChain car = DTOConvert.convert2(ext, CarChain.class, DTOConvert::testDTOConvert);
        System.out.println(car.toString());
    }
}
