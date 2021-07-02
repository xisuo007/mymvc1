package com.king.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ljq on 2020/3/27 13:17
 */
public class BIOPlainechoServer {

    public static void main(String[] args){

    }

    public void improvedServer(int port) throws IOException {
        //将ServerSocket绑定到指定的端口里
        ServerSocket socket = new ServerSocket(port);
        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(6);
        while (true) {
            //阻塞知道收到新的客户端
            final Socket accept = socket.accept();
            System.out.println("连接来自："+accept);
            //将请求提交给线程池去执行
            pool.execute(() ->{
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    PrintWriter writer = new PrintWriter(accept.getOutputStream(), true);
                    //从客户端读取数据并写回复
                    while (true) {
                        writer.println(reader.readLine());
                        writer.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
