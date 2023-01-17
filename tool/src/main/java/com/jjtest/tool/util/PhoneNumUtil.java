package com.jjtest.tool.util;

import cn.hutool.core.util.NumberUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class PhoneNumUtil {
    /**
     * 根据秘钥对11位手机号进行编码
     * <p>
     *
     * 为最大限度的接入人工，业务不受影响，出现异常，不处理，返回空的手机号。
     * @param pn  必须为11位手机号
     * @param key key为index:[0,length-2]为不重复的数字，用于转换位置，index:length-1是插入几个随机数的数量
     * @return 手机号编码结果
     */
    public static String encryptPhoneNew(String pn, String key) {
        try {
            if (null == pn || !Pattern.matches("\\d{11}", pn)) {
                throw new RuntimeException("incorrect mobile number, please check! pn=" + pn);
            }

            if (key.length() < 5 || key.length() > 11 || key.length() % 2 == 0) {
                throw new RuntimeException("key size is error, please check!");
            }

            char[] p1 = pn.toCharArray();
            char[] p2 = pn.toCharArray();
            char[] ks = key.toCharArray();
            int[] iks = new int[ks.length -1];
            for (int i = 0; i < ks.length -1 ; i = i + 2) {
                p1[Character.getNumericValue(ks[i])] = p2[Character.getNumericValue(ks[i + 1])];
                p1[Character.getNumericValue(ks[i + 1])] = p2[Character.getNumericValue(ks[i])];
                iks[i] = Character.getNumericValue(ks[i]);
                iks[i+1] = Character.getNumericValue(ks[i+1]);
            }

            Arrays.sort(iks);
            int temp = -1;
            for (int i = 0; i < iks.length -1 ; i++) {
                if (iks[i] == temp) {
                    throw new RuntimeException("key is error, please check!");
                }
                temp = iks[i];
            }

            Map<String, String> charMap = getData();
            Map<String, String> numMap = new HashMap<>();
            for (Map.Entry<String, String> entry : charMap.entrySet()) {
                numMap.put(entry.getValue(), entry.getKey());
            }

            String[] strArry = new String[p1.length];
            for (int i = 0; i < p1.length; i++) {
                strArry[i] = numMap.get(String.valueOf(p1[i]));
            }

            int s = Character.getNumericValue(ks[ks.length - 1]);
            int rd = new Random().nextInt(10000);
            String rs = String.format("%0" + s + "d", rd);
            rs = rs.length() > s ? rs.substring(0, s) : rs;
            char[] rsary = rs.toCharArray();
            for (char c : rsary) {
                int rdm = new Random().nextInt(11);
                strArry[rdm] = c + strArry[rdm];
            }

            int rdm = new Random().nextInt(11);
            strArry[rdm] = "a" + strArry[rdm];
            rdm = new Random().nextInt(11);
            strArry[rdm] = "z" + strArry[rdm];

            StringBuilder stringBuffer = new StringBuilder();
            for (String value : strArry) {
                stringBuffer.append(value);
            }
            return stringBuffer.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 编码映射
     * @return Map
     */
    public static Map<String, String> getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("u", "0");
        map.put("k", "1");
        map.put("d", "2");
        map.put("e", "3");
        map.put("n", "4");
        map.put("b", "5");
        map.put("f", "6");
        map.put("q", "7");
        map.put("c", "8");
        map.put("r", "9");
        return map;
    }

    public static String encrypt(String pn, String key) {
        int length = pn.length();
        String s = pn.replaceAll("\\d+", "");
        int ranNum = NumberUtil.parseInt(key.substring(key.length() - 1));
        if ((length - s.length()) != ranNum) {
            throw new RuntimeException(String.format("随机位数不一致，pn:%s, key: %s", pn, key));
        }
        s = s.replaceAll("[az]", "");
        Map<String, String> charMap = getData();
        String[] charArr = new String[s.length()];
        for (int i=0; i < s.length(); i++) {
            charArr[i] = charMap.get(String.valueOf(s.charAt(i)));
        }
        //位置交换
        char[] ks = key.toCharArray();
        String[] p2 = Arrays.copyOf(charArr, charArr.length);
        for (int i = 0; i < ks.length -1 ; i = i + 2) {
            charArr[Character.getNumericValue(ks[i])] = p2[Character.getNumericValue(ks[i + 1])];
            charArr[Character.getNumericValue(ks[i + 1])] = p2[Character.getNumericValue(ks[i])];
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String pns : charArr) {
            stringBuilder.append(pns);
        }
        return stringBuilder.toString();
    }

}
