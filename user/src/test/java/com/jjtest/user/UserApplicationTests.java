package com.jjtest.user;

import com.alibaba.fastjson.JSONObject;
import com.jjtest.tool.redis.DistributedLock;
import com.jjtest.tool.util.KeyWordLuceneService;
import com.jjtest.user.config.TestConfig;
import com.jjtest.user.po.UserPO;
import com.jjtest.user.service.UserService;
import com.jjtest.user.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil  redisUtil;
    @Autowired
    private TestConfig testConfig;
    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private KeyWordLuceneService keyWordLuceneService;

    String script = "local value1 = tonumber(ARGV[1])\n"
            + "local value2 = tonumber(ARGV[2])\n"
            + "local key = KEYS[1]\n"
            + "local value = tonumber(redis.call('get',key) or 0)\n"
            + "local result = 0\n"
            + "if value1>value2 then result=10\n"
            + "else result = result-1 end\n"
            + "return result";

    @Test
    public void contextLoads() {
        UserPO admin = userService.selectUserByUserName("jj");
        System.out.println(admin);
//        boolean is = redisUtil.set("ff","oooo",20);
//        System.out.println(is);
//        List<String> keys = new ArrayList<>();
//        keys.add("fff");
//        List<String> params = new ArrayList<>();
//        params.add("1");
//        params.add("2");
//        Long aLong = redisUtil.evalLuaReturnLong(script, keys, params);
//        System.out.println(aLong);
//        StringBuilder old = new StringBuilder("6009270598");
//        String in = "01400000";
//        String newS = "600927014000000598";
//        System.out.println(newS);
//        String s = old.insert(6, in).toString();
//        System.out.println(s);
//        System.out.println(newS.equals(s));
//        String substring = StringUtils.substring(newS, newS.length() - 1, newS.length());
//        System.out.println(substring);
        List<UserPO> list = new ArrayList<>();
        UserPO po = new UserPO();
        po.setUserName("jj");
        po.setAge(24);
        list.add(po);
        list.add(po);
        po = new UserPO();
        po.setUserName("ff");
        po.setAge(23);
        list.add(po);
        po = new UserPO();
//        po.setUserName();
        po.setAge(25);
        list.add(po);
        System.out.println(list);
//        list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
//                new TreeSet<>(Comparator.comparing(o -> o.getUserName() + ";" + o.getAge()))), ArrayList::new));
        System.out.println(list);
//        List<Integer> collect = list.stream().map(UserPO::getAge).distinct().collect(Collectors.toList());
//        List<UserPO> collect = list.stream().sorted((po1, po2) -> {
//            return po1.getAge().compareTo(po2.getAge());
//        }).collect(Collectors.toList());
//        System.out.println(list);
//        Collections.shuffle(list);
//        System.out.println(list);
        Map<Integer, String> collect = list.stream().collect(Collectors.toMap(UserPO::getAge, po1 -> {
            return po1.getUserName()==null?"":po1.getUserName();
        }, (k,v)->v));
        System.out.println(collect);

    }

    @Test
    public void test1(){
        String jsonStr = testConfig.getTestff();
        try {
            System.out.println(jsonStr);
            jsonStr = jsonStr.replaceAll("\\\\\\{", "{");
            jsonStr = jsonStr.replaceAll("\\\\\\}", "}");
            System.out.println(jsonStr);
            UserPO po = JSONObject.parseObject(jsonStr, UserPO.class);
            System.out.println(po);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
//        TreeMap<Integer, String> treeMap = new TreeMap<>((a, b)->{
//            if(a < b){
//                return -1;
//            }
//            return 1;
//        });
//        treeMap.put(2, "2");
//        treeMap.put(1, "1");
//        treeMap.put(3, "3");
//        System.out.println(treeMap);
//        String s = "FSQQUESTION发货的IPdssN_D的2232是988Yd";
//        Pattern pattern = Pattern.compile("\\d+");
//        Matcher matcher = pattern.matcher(s);
//        System.out.println(matcher.matches());
//        System.out.println(s.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]","").toLowerCase());
        Object jj = redisUtil.get("jj");
        System.out.println(jj);
        String key = "fengfff";
        String requestId = "212";
        int timeout = 1000*60;
        new Thread(()->{
            boolean b = distributedLock.tryGetDistributedLock(key, requestId, timeout);
            System.out.println(b);
        }).start();
        boolean a = distributedLock.tryGetDistributedLock(key, requestId, timeout);
        System.out.println(a);


    }

    @Test
    public void testKeyword(){
        keyWordLuceneService.initLucene();
        Map<String,String> map = new HashMap<>();
        map.put("3","法院");
        map.put("1","集团升级");
        map.put("2","集团总部");

        keyWordLuceneService.initDirectory(map);

        List<String> list = keyWordLuceneService.searchKeyWord("法院集团升级集团总部人");

        System.out.println(list);
    }


}
