package utils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5_Utils{  
	
	public static String getMD5String(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
    public static void getMD5Value(String str){  
        byte[] secretBytes = null;  
        try {  
            MessageDigest md5 = MessageDigest.getInstance("md5");  
            secretBytes  =  md5.digest(str.getBytes());  
        } catch (NoSuchAlgorithmException e) {  
            new Exception("没有MD5这个算法。").printStackTrace();  
        }   
        System.out.println("length=: " + secretBytes.length);
        for (int i = 0; i < secretBytes.length; i++) {  
            System.out.println(secretBytes[i]);  
        }  
    }  
    
}  
