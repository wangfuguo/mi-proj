package com.hoau.miser.module.common.shared.util;

import java.security.MessageDigest;

public class MD5EncryptUtil {

	private static char[] Digit = new char[] { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String md5(String content, String charset) throws Exception {
		byte[] data = content.getBytes(charset);
		byte[] md5 = md5(data);
		StringBuilder sb = new StringBuilder();
		for (byte b : md5) {
			sb.append(toHexStr(b));
		}
		return sb.toString();
	}

	public static String md5(String content) throws Exception {
		return md5(content, "UTF-8");
	}

	private static byte[] md5(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data);
		return md.digest();
	}

	private static String toHexStr(byte b) {
		char[] c = new char[2];
		c[0] = Digit[(b >>> 4) & 0x0f];
		c[1] = Digit[b & 0x0f];
		return new String(c);
	}

}
