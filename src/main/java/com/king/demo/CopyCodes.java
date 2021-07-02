package com.king.demo;

import java.io.*;

/**
 * @program: ccl-cmos
 * @description: TODO
 * @author: shenshuiliang
 * @create: 2019-03-11 09:44
 **/
public class CopyCodes {
    public static int i;//用于统计代码行数
    public static String filePath = "E:/workspace/kingcar/ccl-cmos";
    public static String outfile = "D:/ccl-coms.txt";
    public static void main(String[] args) {
        //需要搜索的目录
        File f = new File(filePath);
        inDir(f);
        System.out.println("执行完毕！！！");
    }
    // 遍历目录f中的各个文件及子目录
    public static void inDir(File f) {
        File[] codeFile = f.listFiles();
        //逐一判定
        for (File file : codeFile) {
            //是目录但不是名称为extlib的目录（排除其中不需要搜索的目录）
            if (file.isDirectory() && !"extlib".equals(file.getName())) {
                inDir(file);//是目录遍历之
            } else if (file.getName().matches(".*\\.java")) {//是后缀为.java的文件
                parse(file);//逐行读取文件内容
            }
        }
    }
    // 读取没行文字并判断
    public static void parse(File file) {
        String fileParent = file.getParent();
        String fileName = fileParent.substring(11) + "\\" + file.getName();//获取文件名
        BufferedReader br = null;
        try {
            writeInFile("");//每写完一个文件的代码空一行
            writeInFile(fileName);//写代码前先表明文件名
            InputStreamReader fr = new InputStreamReader(new FileInputStream(file), "utf-8");//编码转换
            br = new BufferedReader(fr);
            String line = "";
            //逐行读取，并写入文件
            while ((line = br.readLine()) != null) {
                //判定是否为空行
                if (!line.trim().matches("^[\\s&&[^\\n]]*$")) {

                    writeInFile(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //将传入的字符串写入指定文件
    public static void writeInFile(String str) throws IOException {
        File tofile = new File(outfile);//要写入的目标文件
        byte[] b = str.getBytes();
        byte[] newLine = "\r\n".getBytes();//用于换行
        //防止数据覆盖，设置文件的写入方式为追加
        FileOutputStream fos = new FileOutputStream(tofile, true);
        fos.write(b);
        fos.write(newLine);
        fos.close();
        System.out.println(++i);
    }

}
