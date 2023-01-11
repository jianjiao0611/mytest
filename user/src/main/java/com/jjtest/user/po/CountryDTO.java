package com.jjtest.user.po;

public class CountryDTO {
    private String country;

    public void setCountry(String country) {
        System.out.println("setCountry");
        this.country = country;
    }

    public String getCountry() {
        System.out.println("getCountry");
        return this.country;
    }

    public Boolean isChinaName() {
        System.out.println("isChinaName 执行");
//        return this.country.equals("中国");
        return true;
    }

    public String find() {
        System.out.println("find 执行");
        return "ff";
    }

    public String queryF() {
        System.out.println("queryF 执行");
        return "ff";
    }

    public String getFf() {
        System.out.println("getFf");
        return "66666";
    }
}
