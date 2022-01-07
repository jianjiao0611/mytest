package com.jjtest.tool.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtils2 {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    private List<String> list = new ArrayList<String>() {{
        this.add("");
    }};

    /**
     * date 转string
     * @param date
     * @param formatter
     * @return
     */
    public static String dateToString(Date date, String formatter) {
        return  LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern(formatter));
    }

    public static void main(String[] args) {
        Map<Integer, List<String>> map = new HashMap<>();
        String str = "{\"11\":[\"11\",\"13\"]}";

        Map<Integer, List<String>> formatter = JSONObject.parseObject(str, new TypeReference<Map<Integer, List<String>>>() { });

        for (Map.Entry<Integer, List<String>> entry : formatter.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            System.out.println("k=" + entry.getKey() + ",v=" + entry.getValue());
        }
        System.out.println(map.get(11));
    }

}
