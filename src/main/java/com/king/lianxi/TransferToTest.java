package com.king.lianxi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * transferTo NIO里相当于零拷贝的方式来处理文件相对于传统的文件处理要快点
 * @Author ljq
 * @Date 2019/11/4 10:50
 */
public class TransferToTest {

    public static void main(String[] args) throws IOException{
        copyFileByChannel(new File("D:\\ccl-coms.txt"),new File("D:\\ccl-cmos.txt"));
    }

    public static void copyFileByChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = new FileInputStream(source).getChannel();
        FileChannel targetChannel = new FileOutputStream(dest).getChannel();
        for (long count = sourceChannel.size(); count > 0;){
            long transferTo = sourceChannel.transferTo(sourceChannel.position(), count, targetChannel);
            count -= transferTo;
        }
    }
}
