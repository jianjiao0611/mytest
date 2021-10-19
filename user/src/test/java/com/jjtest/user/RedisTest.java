package com.jjtest.user;

import com.jjtest.user.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test1(){
//        Object jj = redisUtil.get("jj");
//        System.out.println(jj);

        boolean set = redisUtil.set("wj", "hahahaha");
        System.out.println(set);
    }

    public static void main(String[] args) {
        List<Integer> ls = new ArrayList<>();
        ls.add(3);
        ls.add(1);
        ls.add(5);
        ls.add(6);
        ls.add(2);
        ls.add(4);
        System.out.println(ls);
        ls.sort(((o1, o2) -> {
            return o2.compareTo(o1);
        }));

        System.out.println(ls);

        System.out.println(String.format("测试%d条,上销售%d",2,5));
    }

}
