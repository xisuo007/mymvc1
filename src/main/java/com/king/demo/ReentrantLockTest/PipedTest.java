package com.king.demo.ReentrantLockTest;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Created by ljq on 2019/3/12 18:01
 */
public class PipedTest {
    public static void main(String[] args) throws Exception{
        new PipedTest().piped();
    }
    public static void piped() throws Exception{
        final PipedWriter writer = new PipedWriter();
        final PipedReader reader = new PipedReader();

        writer.connect(reader);
        Thread T1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("开始写第"+i+"个");
                    try {
                        writer.write(i+"");
                        System.out.println("管道写入了+++++："+i);
                        Thread.sleep(10);
                    } catch (Exception e) {

                    }finally {
                        try {
                            writer.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Thread T2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int msg = 0;
                try {
                    while ((msg = reader.read())!=-1){
                        System.out.println("管道读取了：------"+msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        T1.start();
        T2.start();
    }
}
