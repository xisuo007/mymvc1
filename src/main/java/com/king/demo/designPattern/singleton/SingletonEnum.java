package com.king.demo.designPattern.singleton;


/**
 * Created by ljq on 2019/3/21 17:28
 */
public enum  SingletonEnum {
    INSTANCE;
    private String objName;

    public String getObjName() {
        return objName;
    }


    public void setObjName(String objName) {
        this.objName = objName;
    }

    public static void main(String[] args){
        SingletonEnum first = SingletonEnum.INSTANCE;
        first.setObjName("firstName");
        System.out.println(first.getObjName());
        SingletonEnum second = SingletonEnum.INSTANCE;
        second.setObjName("second");
        System.out.println(first.getObjName());
        System.out.println(second.getObjName());

        //反射获取实例
        try {
            SingletonEnum[] enumConstants = SingletonEnum.class.getEnumConstants();
            for (SingletonEnum enumConstant : enumConstants) {
                System.out.println(enumConstant.getObjName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
