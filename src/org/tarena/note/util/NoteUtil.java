package org.tarena.note.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class NoteUtil {
	/**
	 * 采用MD5摘要算法处理
	 * @return
	 */
	public static String md5(String msg){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] input=msg.getBytes();
			byte[] output=md.digest(input);
			//采用Base64算法将字节数组转换成字符串
			String base64_msg=Base64.encodeBase64String(output);
			return base64_msg;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
		}
		return null;
	}
	
	public static String createId(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static String createToken(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	
	public static byte[] encode2bytes(String source) {
		byte[] result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(source.getBytes("UTF-8"));
			result = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 将源字符串使用MD5加密为32位16进制数
	 * @param source
	 * @return
	 */
	public static String encode2hex(String source) {
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(source.getBytes("UTF-8"));
			byte[] data = md.digest();
			
			for (int i = 0; i < data.length; i++) {
				String hex = Integer.toHexString(0xff & data[i]);
				
				if (hex.length() == 1) {
					hexString.append('0');
				}
				
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
	
	/**
	 * 验证字符串是否匹配
	 * @param unknown 待验证的字符串
	 * @param okHex	使用MD5加密过的16进制字符串
	 * @return	匹配返回true，不匹配返回false
	 */
	public static boolean validate(String unknown , String okHex) {
		return okHex.equals(encode2hex(unknown));
	}
	
	public static void main(String[] args) {
		System.out.println(md5("admin"));
	}
}
