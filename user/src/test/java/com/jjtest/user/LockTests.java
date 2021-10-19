package com.jjtest.user;

import com.alibaba.fastjson.JSONObject;
import com.jjtest.user.po.UserPO;
import com.jjtest.user.po.tree.Node;
import com.jjtest.user.po.tree.Tree;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTests {

    private static Lock lock = new ReentrantLock(true);

    public static void test(){

        for(int i=0;i<2;i++){
            try {
                lock.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "获取了锁");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + "执行");
            }catch (InterruptedException  e){
                System.out.println(Thread.currentThread().getName() + "被中断");
            }finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }
        }
    }

    public static void main(String[] args) {
        try {
//            HashMap map = new HashMap();
//            map.put("",null);
//            Thread ta = new Thread(() ->test(),"线程A");
//            ta.start();
//            Thread tb = new Thread(() -> test(), "线程B");
//            tb.start();
//            ta.interrupt();
//            new Thread(() ->test(),"线程C").start();
//            new Thread(() -> test(), "线程D");
//            new Thread(() ->test(),"线程E").start();
//            FutureTask task = new FutureTask(() ->{
//                test();
//                return "";
//            });
//            task.run();
//            for (;;) {
//                System.out.println("线程A状态：" + ta.getState());
//                System.out.println("线程B状态：" + ta.getState());
//                TimeUnit.SECONDS.sleep(1);
//            }
//            String s = "20.12%";
//            s = s.replace("%","");
//            BigDecimal bigDecimal = new BigDecimal(s);
//            BigDecimal bigDecimal1 = bigDecimal.divide(new BigDecimal(100),4,RoundingMode.HALF_UP);
//            System.out.println(bigDecimal1);

//            ConcurrentMap hashMap = new ConcurrentHashMap<>();
//            hashMap.put("feng","jj");
//
//            hashMap.put("feng","pp");
//            System.out.println(hashMap);

//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M月d日");
//
//            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
//
//            Date date = simpleDateFormat1.parse("20200122");
//            String format = simpleDateFormat.format(date);
//            System.out.println(format);

//            Tree tree  = new Tree();
//            tree.insert("jiaof","f");
//            tree.insert("ff","j");
//            System.out.println(tree.toString());
//            tree.insert("ff","ttt");
//            System.out.println(tree.toString());
//
//            Node node = tree.find("ff");
//            System.out.println(node);


            List<String> list = new ArrayList<>();
            list.add("1");
            list.add("2");
            list.add("3");
            System.out.println(list);
            List<String> list1 = new ArrayList<>();
            list1.addAll(list);
            for(String s:list){
                if(s.equals("1")){
                    list1.add("1.1");
                }
            }
            System.out.println(list1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
