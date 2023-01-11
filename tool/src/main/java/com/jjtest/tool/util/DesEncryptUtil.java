package com.jjtest.tool.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DesEncryptUtil {

    public static void main(String[] args) {
        try {
//            String decrypt = DesEncryptUtil.encryptDesToBase64("991433929", "12345678");
//            System.out.println(decrypt);
//
//            String s = DesEncryptUtil.decrypt(decrypt, "1234567899999");
//            System.out.println(s);

            String s1 = DesEncryptUtil.jkdDes("991433929", "12345678");
            System.out.println(s1);

            String s2 = DesEncryptUtil.jmJdk(s1, "1234567899999");
            System.out.println(s2);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @return 加密后的字符串的base64格式
     * @Description: 加密
     * @Param sourceStr 原始未加密明文 key 密钥，长度必须大于等于8位
     * @Date: 2021/4/8 13:24
     **/
    public static String encryptDesToBase64(String sourceStr, String key) {
        try {
            if (sourceStr == null || sourceStr.isEmpty()) {
                throw new Exception("原文不能为空！");
            }
            if (key == null || key.isEmpty() || key.length() < 8) {
                throw new Exception("密钥不能为空，且密钥必须大于等于8位！");
            }
            byte[] datasource = sourceStr.getBytes("utf-8");
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes("utf-8"));

            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);

            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");

            //用密匙初始化Cipher对象,ENCRYPT_MODE用于将 Cipher 初始化为加密模式的常量
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);

            //现在，获取数据并加密 正式执行加密操作
            //按单部分操作加密或解密数据，或者结束一个多部分操作
            return Base64.encodeBase64String(cipher.doFinal(datasource));

        } catch (Throwable e) {
//            log.error("DES 加密异常，详情：" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 解密后的字符串
     * @Description: 解密
     * @Param encodedStrBase64 加密过的密文base64形式（由加密方法返回的）  key 密钥，长度必须大于等于8位
     * @Date: 2021/4/8 13:27
     **/
    public static String decrypt(String encodedStrBase64, String key) throws Exception {
        if (encodedStrBase64 == null || encodedStrBase64.isEmpty()) {
            throw new Exception("密文不能为空！");
        }

        if (key == null || key.isEmpty() || key.length() < 8) {
            throw new Exception("密钥不能为空，且密钥必须大于等于8位！");
        }

        byte[] src = Base64.decodeBase64(encodedStrBase64);

        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key.getBytes("utf-8"));
        // 创建一个密匙工厂
        // 返回实现指定转换的 Cipher 对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return new String(cipher.doFinal(src));
    }

    public static String jkdDes(String src, String key) throws Exception {
        //1.生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("des");//密钥生成器

//        SecretKey secretKey = keyGenerator.generateKey();//用密钥生成器生成密钥
        byte[] byteKeys = key.getBytes("utf-8");//得到密钥的byte数组
//        keyGenerator.init(24);//指定密钥长度为56位

        //2.key转换
        DESKeySpec desKeySpec = new DESKeySpec(byteKeys);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("des");//秘密密钥工厂
        SecretKey convertSecretKey = factory.generateSecret(desKeySpec);

        //3.加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//见上图的工作模式和填充模式
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);//加密模式
        byte[]result = cipher.doFinal(src.getBytes());

        System.out.println("jdk DES加密：\n" + Hex.encodeHexString(result));
        return Hex.encodeHexString(result);


    }

    public static String jmJdk(String src, String key) throws Exception {
        //1.生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("des");//密钥生成器

//        SecretKey secretKey = keyGenerator.generateKey();//用密钥生成器生成密钥
        byte[] byteKeys = key.getBytes("utf-8");//得到密钥的byte数组
        keyGenerator.init(byteKeys.length);//指定密钥长度为56位

        //2.key转换
        DESKeySpec desKeySpec = new DESKeySpec(byteKeys);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("des");//秘密密钥工厂
        SecretKey convertSecretKey = factory.generateSecret(desKeySpec);

        //4.解密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//见上图的工作模式和填充模式
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);//解密模式
        byte[] result = cipher.doFinal(src.getBytes("utf-8"));
        System.out.println("jdk DES解密：\n" + new String(result));
        return new String(result);
    }
}
