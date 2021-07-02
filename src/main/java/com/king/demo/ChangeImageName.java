package com.king.demo;

import java.io.File;

/**
 * Created by ljq on 2019/4/3 11:39
 */
public class ChangeImageName {
    public static void main(String[] args){
        String path = "E:/image/biaoqing";
        File[] files = new File(path).listFiles();
        int i = 0;
        String fileName = "";
        for (File file : files) {
            fileName = file.getName().substring(file.getName().indexOf("."));
            File to = new File(path+"/"+i+fileName);
            file.renameTo(to);
            i++;
        }

    }
}
