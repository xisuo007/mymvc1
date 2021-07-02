package com.king.poi.hutoolPoi;


/**
 * Created by ljq on 2019/5/10 11:45
 */
public class testt {

    public static void main(String[] args){
        String s1 = "http://files.kingcars.cn/group1/M00/00/00/rBAGVFuI5kqABX-yAAEEWajDVGw524.jpg";
        String s2 = "http://files.kingcars.cn/group1/M00/00/16/rBAGVFu-97eAe6mwAALWFz42wP4090.jpg";
        String str = "http://files.kingcars.cn/group1/M00/00/00/rBAGVFuI5kqABX-yAAEEWajDVGw524.jpg,http://files.kingcars.cn/group1/M00/00/16/rBAGVFu-97eAe6mwAALWFz42wP4090.jpg,http://files.kingcars.cn/group1/M00/00/16/rBAGVFu--FaAdGTdAAM5v3cqmZc832.jpg,http://files.kingcars.cn/group1/M00/00/00/rBAGVFuGZGaADCt_AAKBkJh9pR4054.jpg";
        String newstr = str.replaceAll(s2, s1);
        System.out.println(newstr);
    }
}
