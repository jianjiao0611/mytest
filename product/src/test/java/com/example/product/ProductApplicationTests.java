package com.example.product;

import com.example.product.entity.ProductEntity;
import com.example.product.entity.ProductVo;
import com.example.product.service.Product;
import com.example.product.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {


    /**
     * 配置上下文（也可以理解为配置文件的获取工具）
     */
    @Autowired
    private Environment evn;

    @Autowired
    private Product product;
    @Autowired
    private StudentService studentService;

    @Test
    public void testAbs() {
        studentService.send();

        product.send();
    }

    @Test
    public void threadTest() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StringBuffer stringBuffer = new StringBuffer("1");
        new Thread(()->{
            try {
                System.out.println("子线程开始----");
                Thread.sleep(5000);
                stringBuffer.append("->2");
                System.out.println("子线程结束----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }).start();
        try {
            System.out.println("等待子线程");
            stringBuffer.append("->3");
            boolean await = countDownLatch.await(6, TimeUnit.SECONDS);
            if(await) {
                System.out.println("等到结果"+stringBuffer.toString());
            }else {
                System.out.println("未等到结果" + stringBuffer.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void contextLoads() {

        List<ProductEntity> list = new ArrayList<>();
        ProductEntity entity = new ProductEntity();
        entity.setName("生活");
        entity.setId(1);
        entity.setPid(-1);
        list.add(entity);
        entity = new ProductEntity();
        entity.setId(2);
        entity.setName("牙刷");
        entity.setPid(1);
        list.add(entity);

        entity = new ProductEntity();
        entity.setId(3);
        entity.setName("牙膏");
        entity.setPid(1);
        list.add(entity);

        entity = new ProductEntity();
        entity.setId(4);
        entity.setPid(-1);
        entity.setName("食物");
        list.add(entity);
        entity = new ProductEntity();
        entity.setId(5);
        entity.setPid(4);
        entity.setName("肉");
        list.add(entity);

        entity = new ProductEntity();
        entity.setId(6);
        entity.setName("火腿");
        entity.setPid(5);
        list.add(entity);

        entity = new ProductEntity();
        entity.setId(null);
        entity.setName("蔬菜");
        entity.setPid(4);
        list.add(entity);

        entity = new ProductEntity();
        entity.setId(8);
        entity.setName("白菜");
        entity.setPid(7);
        list.add(entity);

        System.out.println(list);
        List<Integer> collect = list.stream().map(ProductEntity::getId).collect(Collectors.toList());
        System.out.println(collect);
//        List<ProductEntity> collect = list.stream().sorted(Comparator.comparing(ProductEntity::getId,Comparator.nullsFirst(Integer::compareTo)).reversed())
//                .collect(Collectors.toList());
//        System.out.println(collect);

//        List<ProductVo> result = change(list);
//        System.out.println(result);
        List<ProductVo> vos = list.stream().map(po -> {
            ProductVo vo = new ProductVo();
            vo.setId(po.getId());
            vo.setName(po.getName());
            return vo;
        }).collect(Collectors.toList());
        System.out.println(vos);
    }

    private List<ProductVo> change(List<ProductEntity> list) {
        List<ProductVo> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)) {
            Stack<ProductVo> stack = new Stack<>();
            list.forEach(vo->{
                if(vo.getPid().equals(-1)){
                    ProductVo productVo = new ProductVo();
                    productVo.setChild(new ArrayList<>());
                    productVo.setId(vo.getId());
                    productVo.setName(vo.getName());
                    stack.push(productVo);
                    result.add(productVo);
                }
            });
            while (stack.size()>0) {
                ProductVo pop = stack.pop();
                list.forEach(vo->{
                    if(vo.getPid().equals(pop.getId())) {
                        ProductVo productVo = new ProductVo();
                        productVo.setName(vo.getName());
                        productVo.setId(vo.getId());
                        productVo.setChild(new ArrayList<>());
                        pop.getChild().add(productVo);
                        stack.push(productVo);
                    }
                });
            }
        }

        return result;
    }

    public static void main(String[] args) {
//        String s =  "^service_quality_report_day_\\$\\{[\\d]{8}\\}.xlsx$";
//        Pattern pattern=Pattern.compile(s);
//        Matcher matcher = pattern.matcher("service_quality_report_day_${20200523}.xlsx");
//        System.out.println(matcher.matches());
//        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
//            Date parse = simpleDateFormat.parse("2020041010");
//            System.out.println(parse);
//        }catch (Exception e){
//
//        }
//        int a = 113;
//        int b = 2038;
//        Float c = 0.00f;
//        System.out.println(c.floatValue()==0);
//        System.out.println((float)a/b);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(simpleDateFormat.parse("20200901"));
            c2.setTime(simpleDateFormat.parse("20200801"));
//            System.out.println((c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR))*12);
            int i =  c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH) + (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR))*12;
//            System.out.println(i);
            i = Math.abs(i);
            System.out.println(i);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        String str = "1238";
//        str = str.substring(0,str.length()-3);
//        System.out.println(str);
    }

    @Test
    public void test11(){
//        Product bean = SpringUtils.getBean(Product.class);
//        bean.send();
        String property = evn.getProperty("service.url");
        System.out.println(property);
    }

}
