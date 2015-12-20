package com.by.fishenc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class ByEncryptAndDecrypt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    System.out.println("文件加密:"+args.length);
	    String type = args[0];
		String inpath = args[1]; 
		String outpath = args[2];
		System.out.println(inpath);
		System.out.println(outpath);
		youWantType(type, inpath, outpath);
		System.exit(0);
	}
	
	public static void  youWantType(String type,String inpath,String outpath){
		if(type.equalsIgnoreCase("d")){
			//解密
			try {
				decrypt(inpath,"AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746",outpath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//加密 
			try {
				encrypt(inpath,"AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746",outpath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 输入密码的字符形式，返回字节数组形式。 如输入字符串：AD67EA2F3BE6E5AD
	 * 返回字节数组：{173,103,234,47,59,230,229,173}
	 */
	private static byte[] getKeyByStr(String str) {
		byte[] bRet = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i))
					+ getChrInt(str.charAt(2 * i + 1)));
			bRet[i] = itg.byteValue();
		}
		return bRet;
	}

	/**
	 * 计算一个16进制字符的10进制值 输入：0-F
	 */
	private static int getChrInt(char chr) {
		int iRet = 0;
		if (chr == "0".charAt(0))
			iRet = 0;
		if (chr == "1".charAt(0))
			iRet = 1;
		if (chr == "2".charAt(0))
			iRet = 2;
		if (chr == "3".charAt(0))
			iRet = 3;
		if (chr == "4".charAt(0))
			iRet = 4;
		if (chr == "5".charAt(0))
			iRet = 5;
		if (chr == "6".charAt(0))
			iRet = 6;
		if (chr == "7".charAt(0))
			iRet = 7;
		if (chr == "8".charAt(0))
			iRet = 8;
		if (chr == "9".charAt(0))
			iRet = 9;
		if (chr == "A".charAt(0))
			iRet = 10;
		if (chr == "B".charAt(0))
			iRet = 11;
		if (chr == "C".charAt(0))
			iRet = 12;
		if (chr == "D".charAt(0))
			iRet = 13;
		if (chr == "E".charAt(0))
			iRet = 14;
		if (chr == "F".charAt(0))
			iRet = 15;
		return iRet;
	}
	
	public static void encrypt(String fileUrl, String sKey,String destFile) throws Exception {
		
		try {
			if (sKey.length() == 48) {
				byte[] bytK1 = getKeyByStr(sKey.substring(0, 16));
				byte[] bytK2 = getKeyByStr(sKey.substring(16, 32));
				byte[] bytK3 = getKeyByStr(sKey.substring(32, 48)); 
				File file = new File(fileUrl); 
				if(!file.exists()){
					System.out.println("FileNotFound :"+fileUrl);
					return;
				}  
				InputStream in = new FileInputStream(fileUrl);
				OutputStream out = new FileOutputStream(destFile);
				 //得到数据的大小  
			    int length = in.available();      
			    byte [] bytIn = new byte[length]; 
			    in.read(bytIn);
			    // 加密
				byte[] bytOut = encryptByDES(encryptByDES(encryptByDES(bytIn,
						bytK1), bytK2), bytK3);
				
				in.close();
				for (int i = 0; i < bytOut.length; i++) {
					out.write((int) bytOut[i]);
				}
				out.close();
				System.out.println("加密成功out－>"+destFile);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("加密出错");
		} 
	    
	}
	
	
    public static void decrypt(String fileUrl, String sKey,String destFile) throws Exception {
		
		try {
			if (sKey.length() == 48) {
				byte[] bytK1 = getKeyByStr(sKey.substring(0, 16));
				byte[] bytK2 = getKeyByStr(sKey.substring(16, 32));
				byte[] bytK3 = getKeyByStr(sKey.substring(32, 48)); 
				File file = new File(fileUrl); 
				if(!file.exists()){
					return;
				}  
				InputStream in = new FileInputStream(fileUrl);
				OutputStream out = new FileOutputStream(destFile);
				 //得到数据的大小  
			    int length = in.available();      
			    byte [] bytIn = new byte[length]; 
			    in.read(bytIn);
			    // 解密
				byte[] bytOut = decryptByDES(decryptByDES(decryptByDES(bytIn,
						bytK3), bytK2), bytK1);
				
				in.close();
				for (int i = 0; i < bytOut.length; i++) {
					out.write((int) bytOut[i]);
				}
				out.close();
				System.out.println("解密成功");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 
	    
	}
	
	
	/**
	 * 用DES方法加密输入的字节 bytKey需为8字节长，是加密的密码
	 */
	private static byte[] encryptByDES(byte[] bytP, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.ENCRYPT_MODE, sk);
		return cip.doFinal(bytP);
	}

	/**
	 * 用DES方法解密输入的字节 bytKey需为8字节长，是解密的密码
	 */
	private static byte[] decryptByDES(byte[] bytE, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.DECRYPT_MODE, sk);
		return cip.doFinal(bytE);
	}
	

}
