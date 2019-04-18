package com.abc.util;

import java.security.MessageDigest;

/**
  * 對密碼進行加密和驗證
  *
  */
public class MD5Util {
    //十六進制下數字到字符的映射陣列
    private final static String[] hexDigits = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**inputString 加密
     * @param inputString 要進行加密的輸入字符串
     * @return 返回一個MD5加密后的字符串
     */
    public static String generatePassword(String inputString){
        return encodeByMD5(inputString);
    }

    /**
     * 驗證輸入的密碼是否正確
     * @param password 加密后的密碼
     * @param inputString 輸入的字符串
     * @return 驗證結果，true：正確  flase：錯誤
     */
    public static boolean validatePassword(String password,String inputString){
        if(password.equals(encodeByMD5(inputString))){
            return true;
        }else{
            return false;
        }

    }

    //對字符串進行MD5 加密
    private static String encodeByMD5(String originString){
        if(originString != null){
            try{
                //創建具有指定算法名稱的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用指定的字節數組隊摘要進行最後更新，然後完成摘要計算
                byte[] results = md.digest(originString.getBytes());
                //將得到的字節數組變成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 轉換字節數組為十六進制字符串
     * @param b 字節數組
     * @return 十六進制字符串
     */
    private static String byteArrayToHexString(byte[] b){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    //將一個字節轉化成十六進制形式的字符串
    private static String byteToHexString(byte b){
        int n = b;
        if(n < 0) n = 256+n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];

    }
}