package awe.idea.com.common.security;

import org.apache.commons.lang.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {
    private static final String salt = "Q5dveRtlAVUfpdKX"; //必须16位
    /**
     * 加密
     * 
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) throws Exception{
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }

    public static byte[] encrypt(String content) throws Exception{
        return encrypt(content,salt); // 加密
    }

    /**
     * http传输使用，加密
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt2hex(String content) throws Exception{
        byte[] encode = encrypt(content);       //加密
        String code = parseByte2HexStr(encode); //转码
        return code;
    }

    public static String encrypt2hex(String content,String salt) throws Exception{
        byte[] encode = encrypt(content,salt);       //加密
        String code = parseByte2HexStr(encode); //转码
        return code;
    }

    /**
     * 解密
     * 
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) throws Exception{
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密
    }

    public static byte[] decrypt(byte[] content) throws Exception{
        return decrypt(content,salt);
    }

    /**
     * http传输使用，解密
     * @param content
     * @return
     * @throws Exception
     */
    public static String hex2decrypt(String content) throws Exception{
        byte[] decode = parseHexStr2Byte(content);      //解码
        byte[] decryptResult = decrypt(decode, salt);   // 解密
        return new String(decryptResult, "UTF-8");
    }

    public static String hex2decrypt(String content,String salt) throws Exception{
        byte[] decode = parseHexStr2Byte(content);      //解码
        byte[] decryptResult = decrypt(decode, salt);   // 解密
        return new String(decryptResult, "UTF-8");
    }

    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (StringUtils.isBlank(hexStr)) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
//        String content = "消息模板";
//        String password = "12345678";
//        // 加密
//        System.out.println("加密前：" + content);
//        byte[] encode = encrypt(content, password);
//
//        //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
//        String code = parseByte2HexStr(encode);
//        System.out.println("密文字符串：" + code);
//        byte[] decode = parseHexStr2Byte(code);
//        // 解密
//        byte[] decryptResult = decrypt(decode, password);
//        System.out.println("解密后：" + new String(decryptResult, "UTF-8")); //不转码会乱码

//        String username = "mark"; //B012E9673B73F8D03E3922376A43E8EE
//        System.out.println(encrypt2hex(username));

    }
}