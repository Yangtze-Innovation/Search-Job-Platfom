package com.couragehe.util;

import java.util.UUID;

/**
 * 	生成uuid作为主键
 *	 分为64为和32位的
 * @author CourageHe
 *
 */
public class UUIDUtils {
	public static String getId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static String getUUID64() {
		return getId()+getId();
	}
	public static void main(String[]args) {
		System.out.println(getId());
		System.out.println(getUUID64());
	}
}
