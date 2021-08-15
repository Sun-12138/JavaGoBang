package pers.gobang.method;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashEncryption {

    /**
     * 用于字符串哈希加密
     * @param str type
     * @return 返回加密后的数据
     */
    public static String sign(String str, String type) {
        return Encrypt(str, type);
    }

    /**
     * 进行哈希加密
     *
     * @param strSrc 目标字符串
     * @param encName 加密类型
     * @return 返回加密后数据
     */
    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md;
        String strDes;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("签名失败！");
            return null;
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        StringBuilder des = new StringBuilder();
        String tmp;
        for (byte bt : bts) {
            tmp = (Integer.toHexString(bt & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();
    }
}