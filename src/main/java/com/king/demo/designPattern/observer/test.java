package com.king.demo.designPattern.observer;



/**
 * Created by ljq on 2019/3/22 17:00
 */
public class test {
    public static void main(String[] args){
        NoticeData mes = new NoticeData();

        ServerUser1 user1 = new ServerUser1(mes);
        ServerUser2 user2 = new ServerUser2(mes);

        mes.setMsg("快下班了！");
        mes.setMsg("该吃饭了！");
    }

}
