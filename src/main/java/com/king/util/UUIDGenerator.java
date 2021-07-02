package com.king.util;

import java.util.UUID;

/**
 * UUID批量产生器
 *
 */
public class UUIDGenerator {
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		return str;
	}

	/**
	 * 获取不带-的uuid
	 */
	public static String getUUIDSlip(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp =str.replaceAll("-","");
		return temp;
	}

	/**
	 * 获得指定数量的UUID
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
		return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
		ss[i] = getUUID();
		}
		return ss;
	}
}
