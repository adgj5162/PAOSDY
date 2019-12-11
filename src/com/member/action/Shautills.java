package com.member.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Shautills {

	public Shautills() {}
	
	public String encryptSha(String str){
		
		String chgStr = "";
		
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			
			for(int i=0; i<byteData.length; i++){
				sb.append(Integer.toString((byteData[i]&0xff)+0x100, 16).substring(1));
				
			}
			
			chgStr = sb.toString();
		
		} catch (NoSuchAlgorithmException e) {
			chgStr = "";
			e.printStackTrace();
		}
		return chgStr;
	}
}
