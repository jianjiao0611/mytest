package com.jjtest.tool.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

public class JasyptUtil {

    public static void main(String[] args) {
        String jjtest = JasyptUtil.encrypt("123456", "jjtest");
        System.out.println(jjtest);
    }
    /**
     * 加密
     * @param text 需要加密的字符串
     * @return String
     */
    public static String encrypt(String text) {
        return encrypt(text,"scrm");
    }

    /**
     *  解密
     * @param text 需要加密的字符串
     * @param password 加密密码
     * @return String
     */
    public static String encrypt(String text, String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        //自己在用的时候更改此密码
        config.setPassword(password);
        //应用配置
        encryptor.setConfig(config);
        return encryptor.encrypt(text);
    }

    /**
     *  解密
     * @param text 需要加密的字符串
     * @return String
     */
    public static String decrypt(String text) {
        return decrypt(text,"scrm");
    }

    /**
     *  解密
     * @param text 需要解密的字符串
     * @param password 解密密码
     * @return String
     */
    public static String decrypt(String text, String password) {

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        //自己在用的时候更改此密码
        config.setPassword(password);
        //应用配置
        encryptor.setConfig(config);

        //解密
        return encryptor.decrypt(text);
    }
}
