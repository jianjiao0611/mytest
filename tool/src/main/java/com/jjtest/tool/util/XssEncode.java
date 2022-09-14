package com.jjtest.tool.util;

import cn.hutool.http.HTMLFilter;

public class XssEncode {
    public static String xssEncode(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        try {
            HTMLFilter htmlFilter = new HTMLFilter();
            String clean = htmlFilter.filter(s);
            return clean;
        } catch (NullPointerException e) {
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
