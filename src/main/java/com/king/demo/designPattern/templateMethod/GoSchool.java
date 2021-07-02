package com.king.demo.designPattern.templateMethod;

/**
 * Created by ljq on 2019/4/1 16:09
 */
public abstract class GoSchool {
    final void begin(){
        eat();
        howTo();
        school();
    }
    abstract void eat();
    abstract void howTo();
    void school(){
        System.out.println("到达学校！");
    }
}
