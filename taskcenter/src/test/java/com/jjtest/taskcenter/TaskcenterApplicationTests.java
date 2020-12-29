package com.jjtest.taskcenter;

import com.jjtest.taskcenter.client.UserClient;
import com.jjtest.taskcenter.dao.product.ProductDao;
import com.jjtest.taskcenter.dao.user.UserDao;
import com.jjtest.taskcenter.po.ProductPO;
import com.jjtest.taskcenter.po.UserPO;
import com.jjtest.taskcenter.service.ProductService;
import com.jjtest.tool.response.ResultObject;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskcenterApplicationTests {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserClient userClient;

    @Test
    public void contextLoads() {
        UserPO po = new UserPO();
        po.setUserName("jj");
        UserPO userPO = userDao.selectUser(po);
        System.out.println(userPO);

        ProductPO productPO = productDao.selectById(1L);
        System.out.println(productPO);
    }
    @Test
    public void test() {
//        ProductPO productPO = new ProductPO();
//        productPO.setId(1L);
//        productPO.setProductName("fengfeng");
//        productPO.setPrice(new BigDecimal(5.7));
//        productService.editProduct(productPO);
        ResultObject resultObject = userClient.testClient();
        System.out.println(resultObject);



    }

    public static void main(String[] args) {
//        Date now = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(now);
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
//        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
//        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
//        System.out.println(DateUtil.formatAsDatetime(calendar.getTime()));
//
//        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
//        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
//        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
//        System.out.println(DateUtil.formatAsDatetime(calendar.getTime()));
//        BigDecimal bigDecimal = new BigDecimal(1);
//        BigDecimal bigDecimal1 = new BigDecimal(0.4);
//        System.out.println(bigDecimal.subtract(bigDecimal1));
//        System.out.println(bigDecimal);

        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(simpleDateFormat.parse("2008-02-29"));
            System.out.println(simpleDateFormat.format(calendar.getTime()));

            calendar.add(Calendar.YEAR, -1);
            System.out.println(simpleDateFormat.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
