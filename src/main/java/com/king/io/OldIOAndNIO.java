package com.king.io;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ljq on 2019/9/12 9:36
 */
public class OldIOAndNIO {
    public static void main(String[] args){
        //oldIO();
        nio();
    }

    //普通io读取文件的方式
    public static void oldIO(){
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("d:/iotest.txt"));
            byte[] buf = new byte[1024];
            int read = in.read(buf);
            while (read != -1) {
                for (int i = 0; i < read; i++) {
                    System.out.println((char)buf[i]);
                }
                read = in.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public static void nio(){
        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile("d:/iotest.txt","rw");
            FileChannel channel = file.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int read = channel.read(buf);
            System.out.println(read);
            while (read != -1) {
                buf.flip();//将数据写入缓冲区
                while (buf.hasRemaining()) {
                    System.out.println((char)buf.get());
                }
                buf.compact();//将未读取的数据拷贝到buffer起始处，再写数据再它后面写，不会覆盖未读取的数据  clear方法会清空buffer，但是其实数据此时未被清除和覆盖，只不过没有了标记
                read = channel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (file!=null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
