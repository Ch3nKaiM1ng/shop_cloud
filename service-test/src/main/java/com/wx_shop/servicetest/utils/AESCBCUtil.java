package com.wx_shop.servicetest.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Locale;

public class AESCBCUtil {

    //密码
    private static final String key = "12345678901234567890123456789012";
    //iv偏移量
    private static final String iv = "1234567890123456";

    /**
     * 加密：对字符串进行加密，并返回十六进制字符串(hex)
     *
     * @param encryptStr 需要加密的字符串
     * @return 加密后的十六进制字符串(hex)
     */
    public static String encrypt(String encryptStr,String session_key,String IV) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(session_key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(encryptStr.getBytes());

            return byte2HexStr(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * 解密：对加密后的十六进制字符串(hex)进行解密，并返回字符串
     *
     * @param encryptedStr 需要解密的，加密后的十六进制字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String encryptedStr,String session_key,String IV) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(session_key.getBytes("UTF-8"), "AES");


            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);

            byte[] bytes = hexStr2Bytes(encryptedStr);
            byte[] original = cipher.doFinal(bytes);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * 十六进制字符串转换为byte[]
     *
     * @param hexStr 需要转换为byte[]的字符串
     * @return 转换后的byte[]
     */
    public static byte[] hexStr2Bytes(String hexStr) {
        /*对输入值进行规范化整理*/
        hexStr = hexStr.trim().replace(" ", "").toUpperCase(Locale.US);
        //处理值初始化
        int m = 0, n = 0;
        int iLen = hexStr.length() / 2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间

        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + hexStr.substring(i * 2, m) + hexStr.substring(m, n)) & 0xFF);
        }
        return ret;
    }

    /**
     * byte[]转换为十六进制字符串
     *
     * @param bytes 需要转换为字符串的byte[]
     * @return 转换后的十六进制字符串
     */
    public static String byte2HexStr(byte[] bytes) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }
    static {
        //BouncyCastle是一个开源的加解密解决方案，主页在http://www.bouncycastle.org/
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * AES解密
     *
     * @param data      //密文，被加密的数据
     * @param key      //秘钥
     * @param iv       //偏移量
     * @param encodingFormat //解密后的结果需要进行的编码
     * @return
     * @throws Exception
     */
    public static String decryptwx(String data, String key, String iv, String encodingFormat) throws Exception {
//    initialize();
        //被加密的数据
        byte[] dataByte = Base64.decodeBase64(data);
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(key);
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv);


        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));

            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, encodingFormat);
                return result;
            }
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}