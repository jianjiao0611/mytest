package com.jjtest.user;

import com.jjtest.tool.util.MapUtils;
import com.jjtest.user.po.UserPO;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

public class ArthasTest {

    public static void main(String[] args) throws Exception{
        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.jjtest.tool.util.DateUtils.YYYY_MM_DD_HH_MM_SS);
//            Date truncate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
//            System.out.println(simpleDateFormat.format(truncate));
//            List<UserPO> list = new ArrayList<>();
//            while (true){
//                list.add(new UserPO());
//                Thread.sleep(1);
//            }
//            CountDownLatch cld = new CountDownLatch(10);
//
//            CyclicBarrier cyclicBarrier = new CyclicBarrier(10);


            Eat eat = new Eat();
            eat.init();

            System.out.println("准备----------------");
            eat.go();

            System.out.println("------------------------------------");

            Map<String, UserPO> map = new HashMap<>();
            map.put("1",new UserPO(2));
            UserPO po = map.get("1");

            UserPO userPO = new UserPO();





            Optional.ofNullable(po).filter(p -> p.getId() > 1).ifPresent(p -> System.out.println(p.getId()));

            boolean b = Stream.of(1, 2, 3, 4, 5)
                    .anyMatch(i -> {
                        System.out.println(i);
                        return i == 3;
                    });

            List<UserPO> userPOS = new ArrayList<>();
            UserPO userPO1 = new UserPO(1);
            userPOS.add(userPO1);

            userPO1 = new UserPO(2);
            userPOS.add(userPO1);

            userPO1 = new UserPO(3);
            userPOS.add(userPO1);

            Map<String, UserPO> userMap = MapUtils.listToMap(userPOS, "id");
            System.out.println(userMap);

            System.out.println(userMap.get("1"));

//            try(InputStream in = new FileInputStream(new File(""))){
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Eat {

        private Man man = new Man();

        private void init() throws Exception{
            CountDownLatch cld = new CountDownLatch(5);
            //创建线程进入等待
            for (int i = 0; i <5; i++) {
                Thread t = new Thread(() -> {
                    try {
                        //cld.await();//将线程阻塞在此，等待所有线程都调用完start()方法，一起执行
//                            eat.init();
                        synchronized (man) {
                            System.out.println(Thread.currentThread().getName() + "就绪:" + System.currentTimeMillis());
                            cld.countDown();
                            man.wait();
                            System.out.println(Thread.currentThread().getName() + "开始:" + System.currentTimeMillis());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                t.start();
            }
            //等所有线程准备就绪
            cld.await();
        }

        //唤醒所有线程起跑
        private void go() {
            synchronized (man) {
                man.notifyAll();
                System.out.println("开始------------------");
            }
        }
    }

    static class Man {

    }

}
