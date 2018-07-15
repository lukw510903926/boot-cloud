package com.tykj.cloud.common.util;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * java使用PKCS7Padding进行AES加密兼容IOS
 * @author lhy
 *
 */
public class Encrypt {
  
    public static boolean initialized = false;  
      
    public static final String ALGORITHM = "AES/ECB/PKCS7Padding";  
      
    /** 
     * @param  str  要被加密的字符串
     * @param  key  加/解密要用的长度为32的字节数组（256位）密钥
     * @return byte[]  加密后的字节数组 
     */  
    public static byte[] aes256Encode(String str, byte[] key){
        initialize();  
        byte[] result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key  
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);  
            result = cipher.doFinal(str.getBytes("UTF-8"));  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    /** 
     * @param bytes  要被解密的字节数组
     * @param key    加/解密要用的长度为32的字节数组（256位）密钥
     * @return String  解密后的字符串 
     */  
    public static String Aes256Decode(byte[] bytes, byte[] key){  
        initialize();  
        String result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key  
            cipher.init(Cipher.DECRYPT_MODE, keySpec);  
            byte[] decoded = cipher.doFinal(bytes);  
            result = new String(decoded, "UTF-8");  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    public static void initialize(){  
        if (initialized) return;  
        Security.addProvider(new BouncyCastleProvider());  
        initialized = true;  
    }
    
    public static void main(String[] args) {
    	String decodeStr = new String(Aes256Decode(aes256Encode("港府撒的故事", "17706a5106b04b5c".getBytes()), "17706a5106b04b5c".getBytes()));
    	 System.out.println(decodeStr);
	}
}