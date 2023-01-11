package com.jjtest.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjtest.user.po.CountryDTO;
import com.jjtest.user.po.UserPO;
import com.jjtest.user.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    private static final String script = "local szFullString = redis.call('get', KEYS[1]) ; " +
            "if redis.call('exists', KEYS[1]) == 0 then" +
            "   return 1 end " +
            "local szSeparator = ',' " +
            "local nFindStartIndex = 1\n" +
            "local nSplitIndex = 1\n" +
            "local nSplitArray = {}\n" +
            "while true do\n" +
            "   local nFindLastIndex = string.find(szFullString, szSeparator, nFindStartIndex)\n" +
            "   if not nFindLastIndex then\n" +
            "    nSplitArray[nSplitIndex] = string.sub(szFullString, nFindStartIndex, string.len(szFullString))\n" +
            "    break\n" +
            "   end\n" +
            "   nSplitArray[nSplitIndex] = string.sub(szFullString, nFindStartIndex, nFindLastIndex - 1)\n" +
            "   nFindStartIndex = nFindLastIndex + string.len(szSeparator)\n" +
            "   nSplitIndex = nSplitIndex + 1\n" +
            "end " +
            "local res = ARGV[1]..'\",\"'..ARGV[2]..'\",\"'..ARGV[3];" +
            "local result = {}" +
            "local num = 1;" +
            "for i,v in ipairs(nSplitArray) do" +
            "   if i == 1 then" +
            "       v = string.sub(v,2)" +
            "   end " +
            "   if i == 10 then " +
            "       v = string.sub(v,0,string.len(v)-1)" +
            "   end " +
            "   v = string.sub(v, 2, string.len(v)-1)" +
            "   if num == 8 then" +
            "       break" +
            "   end" +
            "   if ARGV[1] == v then " +
            "   elseif ARGV[2] == v then" +
            "   elseif ARGV[3] == v then" +
            "   else num = num + 1 " +
            "       res=res..'\",\"'..v " +
            "       result[num]=v end " +
            "end " +
            " res = '[\"'..res..'\"]' " +
            "redis.call('set',KEYS[1], res) ";

    @Test
    public void test1(){
//        Object jj = redisUtil.get("jj");
//        System.out.println(jj);

//        boolean set = redisUtil.set("wj", "hahahaha");
//        System.out.println(set);

//        List<String> list = Arrays.asList("a","b","c","d","e","f","g","h","i","j");
//        boolean ids = redisUtil.set("ids", JSONObject.toJSONString(list));
//        redisUtil.del("ids");
//        Object ids2 = redisUtil.evalLuaReturn(script, Arrays.asList("ids"), Arrays.asList("a","b","c"));
//        System.out.println(ids2);

//        String ids1 = (String) redisUtil.get("FAQ_CHANNEL_KNG_TOP_111210");
//        System.out.println(ids1);
//        System.out.println(JSONObject.parseArray(ids1, String.class));
        try {
            redisUtil.hsetJ("protalSeq", "110103f71446-cf3c-4a86-9dd8-2f3882057d0611", "ff");

            redisUtil.hsetJ("protalSeq", "110103f71446-cf3c-4a86-9dd8-2f3882057d0612", "ff");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSerialize() {
        CountryDTO countryDTO = new CountryDTO();
        String str = JSON.toJSONString(countryDTO);
        System.out.println(str);
    }

    public static void main(String[] args) throws Exception{
//        List<Integer> ls = new ArrayList<>();
//        ls.add(3);
//        ls.add(1);
//        ls.add(5);
//        ls.add(6);
//        ls.add(2);
//        ls.add(4);
//        System.out.println(ls);
//        ls.sort(((o1, o2) -> {
//            return o2.compareTo(o1);
//        }));
//
//        System.out.println(ls);
//        System.out.println(2);
//        System.out.println(1);
//
//        System.out.println(String.format("测试%d条,上销售%d",2,5));

//        int a=2;
//        int b=5;
//        System.out.println((double) (a/b)  * 100);
//
//        System.out.println( (double)a/b * 100);
//        System.out.println(new BigDecimal(99999.235).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
//
//        System.out.println(new BigDecimal(10000000.235).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
//
//        System.out.println(1.23456781f == 1.23456782f);

//        String s = "test/1.5.1.txt";
//        System.out.println(s.substring(s.lastIndexOf("/"),s.indexOf(".")));

        List<UserPO> list = new ArrayList<>();
        UserPO userPO = new UserPO();
        userPO.setId(1);
        userPO.setAge(11);
        list.add(userPO);

        userPO = new UserPO();
        userPO.setId(2);
        userPO.setAge(11);
        list.add(userPO);

        userPO = new UserPO();
        userPO.setId(3);
        userPO.setAge(12);
        list.add(userPO);

        Map<Integer, List<UserPO>> collect = list.stream().collect(Collectors.groupingBy(UserPO::getAge));
        System.out.println(collect);

//        Map<Integer, List<UserPO>> collect = Optional.ofNullable()
//                .orElseGet(Collections::emptyList)
//                .stream()
//                .filter(po -> StringUtils.isNotBlank(po.getUserName()))
//                .collect(Collectors.groupingBy(UserPO::getAge));
//        System.out.println(collect);
    }

}
