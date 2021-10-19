package com.jjtest.user;

import com.jjtest.user.po.UserPO;
import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArthasTest {

    public static void main(String[] args) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.jjtest.tool.util.excel.DateUtils.YYYY_MM_DD_HH_MM_SS);
            Date truncate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            System.out.println(simpleDateFormat.format(truncate));
//            List<UserPO> list = new ArrayList<>();
//            while (true){
//                list.add(new UserPO());
//                Thread.sleep(1);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
