package com.jjtest.tool.util;


import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.convert.Convert;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES64 {
//    private final Cipher cipher;
//    private final SecretKeySpec key;
//    private AlgorithmParameterSpec spec;
    // 密钥
    public static final String SEED_16_CHARACTER = "1234567812345678";

//    public AES64() throws Exception {
//        // hash password with SHA-256 and crop the output to 128-bit for key
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));
//        byte[] keyBytes = new byte[32];
//        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
//        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
//        AlgorithmParameterSpec spec = getIV();
//    }

    public AlgorithmParameterSpec getIV() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    /**
     * 加密
     * @param content 加密前字符串
     * @param password 加密key 16位字符串
     * @param vector 偏移量 16位字符串
     * @return 加密后字符串
     * @throws Exception
     */
    public String encrypt(String content, String password, String vector) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes("utf-8"),  "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes("utf-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] anslBytes = content.getBytes("utf-8");
        byte[] encrypted = cipher.doFinal(anslBytes);
        return Base64Encoder.encode(encrypted);
    }

    public String decrype(String content, String password, String vector) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes("utf-8"),  "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes("utf-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = Base64Decoder.decode(content);
        byte[] bytes = cipher.doFinal(encrypted);
        return new String(bytes, "utf-8");
    }

    /**
     * 加密
     * @param content 加密前字符串
     * @param password 加密key 16位字符串
     * @return 加密后字符串
     * @throws Exception
     */
    public String encryptNew(String content, String password) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes("utf-8"),  "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] anslBytes = content.getBytes("utf-8");
        byte[] encrypted = cipher.doFinal(anslBytes);
        return byte2hex(encrypted);
    }

    /**
     * 解密
     * @param content
     * @param password
     * @return
     * @throws Exception
     */
    public String decrypeNew(String content, String password) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes("utf-8"),  "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encrypted = hex2byte(content);
        byte[] bytes = cipher.doFinal(encrypted);
        return new String(bytes, "utf-8");
    }

    public String byte2hex(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = (Integer.toHexString(b[i] & 0XFF));
            if (stmp.length() == 1) {
                stringBuilder.append("0");
                stringBuilder.append(stmp);
            } else {
                stringBuilder.append(stmp);
            }
        }
        return stringBuilder.toString();
    }

    public  byte[] hex2byte(String str) {
        byte[] result = new byte[str.length() / 2];
        for (int i=0; i < str.length()/2; i++) {
            result[i] = (byte) Integer.parseInt(str.substring(i * 2, i*2 + 2), 16);
        }
        return result;
    }




    public static void main(String[] args) throws Exception {
        AES64 aes64 = new AES64();
        String jjtest = aes64.encryptNew("chenjie6", "096d3baa0d1344a8");
        System.out.println(jjtest);

        String decrype = aes64.decrypeNew("20561c64e6388484370d3b36c1ef559a", "096d3baa0d1344a8");
        System.out.println(decrype);
//        UUID uuid = UUID.randomUUID();
//        System.out.println(uuid);

//        String decrypt = aes64.decrypt(jjtest);
//        System.out.println(decrypt);
    }

//    /// <summary>
//    /// AES加密，并且有向量
//    /// </summary>
//    /// <param name="encrypteStr">需要加密的明文</param>
//    /// <param name="key">秘钥</param>
//    /// <param name="vector">向量</param>
//    /// <returns>密文</returns>
//    public static string AESEncryptedString(this string encrypteStr, string key, string vector)
//    {
//        byte[] aesBytes = Encoding.UTF8.GetBytes(encrypteStr);
//
//        byte[] aesKey = new byte[16];
//        //直接转
//        Array.Copy(Convert.FromBase64String(key), aesKey, aesKey.Length);
//        byte[] aesVector = new byte[16];
//        //直接转
//        Array.Copy(Convert.FromBase64String(vector), aesVector, aesVector.Length);
//
////        Rijndael Aes = Rijndael.Create();
//        //或者采用下方生成Aes
//        //RijndaelManaged Aes = new();
//        RijndaelManaged Aes = new RijndaelManaged();
//        Aes.Mode = CipherMode.CBC;
//        Aes.Padding = PaddingMode.PKCS5;
//        Aes.Key = aesKey;
//        Aes.IV = aesVector;
//
//
//        // 开辟一块内存流
//        using MemoryStream memoryStream = new MemoryStream();
//        // 把内存流对象包装成加密流对象
//        using CryptoStream cryptoStream = new(memoryStream, Aes.CreateEncryptor(), CryptoStreamMode.Write);
//        // 明文数据写入加密流
//        cryptoStream.Write(aesBytes, 0, aesBytes.Length);
//        cryptoStream.FlushFinalBlock();
//
//        string result = Convert.ToBase64String(memoryStream.ToArray());
//        return result;
//    }
}
