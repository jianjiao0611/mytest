package com.jjtest.user;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jjtest.tool.redis.DistributedLock;
import com.jjtest.tool.util.*;
import com.jjtest.tool.util.sftp.FtpSitePo;
import com.jjtest.tool.util.sftp.SFtpUtil;
import com.jjtest.tool.util.sftp.SftpChannelInfo;
import com.jjtest.tool.util.threadpool.AsyncInvoke;
import com.jjtest.user.config.TestConfig;
import com.jjtest.user.dao.UserMapper;
import com.jjtest.user.po.*;
import com.jjtest.user.service.*;
import com.jjtest.user.service.eat.factory.AnimalFactory;
import com.jjtest.user.util.RedisUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationTests {
    @Autowired
    private IUser userService;
    @Autowired
    private RedisUtil  redisUtil;
    @Autowired
    private TestConfig testConfig;
    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private KeyWordLuceneService keyWordLuceneService;
    @Autowired
    private AsyncInvoke asyncInvoke;
    @Autowired
    private AnimalFactory animalFactory;
    @Autowired
    private SpringUtils springUtils;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentService studentService;
    @Resource(name = "userService")
    private IUser iUser;

    @Autowired
    private SpringUtils1 springUtils1;

    String script = "local value1 = tonumber(ARGV[1])\n"
            + "local value2 = tonumber(ARGV[2])\n"
            + "local key = KEYS[1]\n"
            + "local value = tonumber(redis.call('get',key) or 0)\n"
            + "local result = 0\n"
            + "if value1>value2 then result=10\n"
            + "else result = result-1 end\n"
            + "return result";

    @Test
    public void testLUA(){
        List<String> keys = new ArrayList<>();
        keys.add("fff");
        List<String> params = new ArrayList<>();
        params.add("1");
        params.add("2");
        Long aLong = redisUtil.evalLuaReturnLong(script, keys, params);
    }

    @Test
    public void contextLoads() {
//        UserPO admin = userService.selectUserByUserName("jj");
//        System.out.println(admin);
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
        po.setPassword("222");
        po.setSex(1);
        po.setPhone("1111333");
        po.setAge(1);
        list.add(po);
        list.add(po);
        po = new UserPO();
        po.setUserName("ff");
        po.setAge(2);
        po.setPassword("222");
        po.setSex(1);
        po.setPhone("1111333");

        list.add(po);
        po = new UserPO();
        po.setUserName("1");
        po.setAge(3);
        po.setPassword("222");
        po.setSex(1);
        po.setPhone("1111333");
        list.add(po);
        List<Integer> collect = list.stream().map(UserPO::getId).collect(Collectors.toList());
        String collect1 = list.stream().map((userPO)->{
            return userPO.getAge() + "("+userPO.getUserName()+")";
        }).collect(Collectors.joining(","));
        System.out.println(collect1);
//        int i = userMapper.batchInsertUser(list);
//        System.out.println(i);

//        list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
//                new TreeSet<>(Comparator.comparing(o -> o.getUserName() + ";" + o.getAge()))), ArrayList::new));
//        List<Integer> collect = list.stream().map(UserPO::getAge).distinct().collect(Collectors.toList());
//        List<UserPO> collect = list.stream().sorted((po1, po2) -> {
//            return po1.getAge().compareTo(po2.getAge());
//        }).collect(Collectors.toList());
//        System.out.println(list);
//        Collections.shuffle(list);
//        System.out.println(list);
//        Map<Integer, String> collect = list.stream().collect(Collectors.toMap(UserPO::getAge, po1 -> {
//            return po1.getUserName()==null?"":po1.getUserName();
//        }, (k,v)->v));
//        System.out.println(collect);


    }

    @Test
    public void test1(){
//        String jsonStr = testConfig.getTestff();
//        try {
//            System.out.println(jsonStr);
//            jsonStr = jsonStr.replaceAll("\\\\\\{", "{");
//            jsonStr = jsonStr.replaceAll("\\\\\\}", "}");
//            System.out.println(jsonStr);
//            UserPO po = JSONObject.parseObject(jsonStr, UserPO.class);
//            System.out.println(po);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        UserService bean = SpringUtils1.getBean(UserService.class);
        System.out.println(bean);

        UserPO userPO = new UserPO();
        List<UserPO> userPOS = Optional.of(userPO)
                .map(userService::selectUserList)
                .orElseGet(Collections::emptyList);

        Map<Integer, List<UserPO>> collect = Optional.ofNullable(userService.selectUserList(userPO))
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(po -> StringUtils.isNotBlank(po.getUserName()))
                .collect(Collectors.groupingBy(UserPO::getAge));
        System.out.println(collect);


    }

    @Test
    public void test2(){
        UserPO po = iUser.selectUserByUserName("jj");
        System.out.println(po);
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
//        Object jj = redisUtil.get("jj");
//        System.out.println(jj);
//        String key = "fengfff";
//        String requestId = "212";
//        int timeout = 1000*60;
//        new Thread(()->{
//            boolean b = distributedLock.tryGetDistributedLock(key, requestId, timeout);
//            System.out.println(b);
//        }).start();
//        boolean a = distributedLock.tryGetDistributedLock(key, requestId, timeout);
//        System.out.println(a);

        List<UserPO> userPOS = new ArrayList<>();
        UserPO userPO = new UserPO(1);
        userPOS.add(userPO);

        List<Runnable> actions = userPOS.stream().map(p -> (Runnable) () ->
                System.out.println(p.getId() + ":" +System.currentTimeMillis())).collect(Collectors.toList());

        CountDownLatch countDownLatch = new CountDownLatch(10);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 5, TimeUnit.MINUTES, new LinkedBlockingDeque<>());
        actions.forEach(executor::execute);


//        for (int i=0; i<10; i++) {
//
//
//            asyncInvoke.submit(()->{
//
//                boolean jjkey = distributedLock.tryLock("jjkey", "ff",50000, 5, 1100);
//                System.out.println(jjkey);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                distributedLock.releaseDistributedLock("jjkey","ff");
//                countDownLatch.countDown();
//            });
//        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




    @Test
    public void testKeyword(){
//        keyWordLuceneService.initLucene();
//        Map<String,String> map = new HashMap<>();
//        map.put("3","法院");
//        map.put("1","集团升级");
//        map.put("2","集团总部");
//
//        keyWordLuceneService.initDirectory(map);
//
//        List<String> list = keyWordLuceneService.searchKeyWord("法院集团升级集团总部人");
//
//        System.out.println(list);

        try {
            List<Entity> user = Db.use().findAll("user_info");
            System.out.println(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void factoryTest() {
//        IEatService ieatService = animalFactory.getIeatService(AnimalEnum.DOG);
//        ieatService.eat();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        try {
//            Date parse = simpleDateFormat.parse("2020-01-02 00:23:00 23:59:59");
//            System.out.println(parse);
//            System.out.println(simpleDateFormat.format(parse));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String s = Convert.digitUppercase(4000);
//        System.out.println(s);
//        BigDecimal n = new BigDecimal("-0.021");
//        DecimalFormat df = new DecimalFormat("0.00%");
//        float i = -0.0021f;
//        String format = df.format(n);
//
//        System.out.println(format);
//        List<String> list = new ArrayList<>();
//        list.add("ff");
//        list.add("天津");
//        list.add("北京");
//        System.out.println(list);
//
//        System.out.println(list.remove("北京"));
//
//        System.out.println(list.remove("上海"));
//        System.out.println(list);
//        Integer a=127,b=127,c=128,d=128;
//        Integer e = new Integer(127);
//        int f=128;
//        Integer g = new Integer(128);
//        System.out.println(a==b);
//        System.out.println(c==d);
//        System.out.println(d==f);
//
//        System.out.println(f == g);

        FtpSitePo po = new FtpSitePo();
        po.setIp("192.168.2.91");
        po.setPort(22);
        po.setAccount("sftp");
        po.setPassword("123");
        SftpChannelInfo channelSftp = SFtpUtil.getChannelSftp(po);
        String floder = "/tmp/test/";


        InputStream inputStream = null;

        try {
            File folder = new File(floder);
            if (!folder.exists()){
                boolean mkdirs = folder.mkdirs();
                if (!mkdirs) {
                    return;
                }
            }
            File file = new File(floder + "test.txt");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("xxxaffdf");
            bw.flush();
            bw.close();
            fw.close();
            inputStream = new FileInputStream(file);

            SFtpUtil.uploadFile(channelSftp, "/data/test","test.txt",inputStream);
//            SFtpUtil.change(floder, floder + "d", channelSftp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DeleteFileUtil.delTmpFolder(floder);
            IOUtils.closeQuietly(inputStream);
        }

//        UserPO jj = userService.selectUserByUserName("jj");
//        jj.setAge(22);
//        studentService.updateUser(jj);
//        UserService userService = new UserService();
//        ClassLoader classLoader = UserService.class.getClassLoader();
//        System.out.println(classLoader);
//        Class<?>[] interfaces = userService.getClass().getInterfaces();
//        System.out.println(interfaces);
//        IUser service = (IUser) Proxy.newProxyInstance(classLoader, interfaces, new MyInvocationHandler(UserService.class));
//
//        UserPO jj = service.selectUserByUserName("jj");
//        System.out.println(jj);

    }


    public static void main(String[] args) {
//        UserService userService = new UserService();
//        ClassLoader classLoader = UserService.class.getClassLoader();
//        System.out.println(classLoader);
//        Class<?>[] interfaces = userService.getClass().getInterfaces();
//        System.out.println(interfaces);
//        IUser service = (IUser) Proxy.newProxyInstance(classLoader, interfaces, new MyInvocationHandler(userService));
//
//        UserPO jj = service.selectUserByUserName("jj");
//        System.out.println(jj);
//        List<String> list = new ArrayList<>();
//        list.add("2");
//        list.add("");
//        list.add("3");
//
//        List<String> list1 = new ArrayList<>();
//        list1.add("2");
//        list1.add("");
//        list1.add("4");
//        list1.add("3");
//
//        a:for (int i=0; i<list.size();i++) {
//            b:for(int j=0; j<list1.size();j++){
//                if(list.get(i).equals(list1.get(j))){
//                    System.out.println(list.get(i));
//                    break b;
//                }
//            }
//        }
//        List<UserPO> list = new ArrayList<>();
//
//        UserPO po = new UserPO();
//        po.setUserName("jj");
//        po.setPassword("222");
//        po.setAge(11);
//
//
//
//        UserPO po1 = new UserPO();
//        po1.setUserName("ff");
//        po1.setPassword("333");
//        list.add(po1);
//
//        po1 = new UserPO();
//        po1.setUserName("pp");
//        po1.setPassword("11");
//        list.add(po1);
//
//        po.setList(JSONObject.toJSONString(list));
//        try {
//            String json = JSONObject.toJSONString(po);
//            System.out.println(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String s = "12333,3333";
//        System.out.println(Arrays.asList(s.split(",")).size());

//        UserPO po = new UserPO();
//
//        BasePo basePo = new BasePo();
//
//        StudentPo studentPo = new StudentPo();
//        System.out.println(studentPo instanceof  BasePo);
//
//        System.out.println(po instanceof  BasePo);

//        Integer a = new Integer(9999);
//        System.out.println(a == 9999);

//        String str = "200223,200344,433434";
//        System.out.println(str.contains("200234"));
//
//        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<CustomServiceInterfaceResponse><header><servicename>queryUseHistoryD</servicename><version>1.0</version><timestamp>1638774523101</timestamp><Digest>MzNENzIyMUM2RkM1MjAzQjlGMDc2NURFQUM2MzRCMzk=</Digest><ConversationId>20211206150843120042</ConversationId></header><body><returnCode>000000</returnCode><description>成功</description><retCode>0</retCode><retMsg>success</retMsg><total>68</total><datalist><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600798</productId><useBeginTime>2021-11-07 13:22:46</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频体育赛事按次18元</productName><flowKB>8667</flowKB><programName>UFC第268期 （郑文祺、尚志法、娄一晨、张光宇、盛宇）</programName><durationSec>1220</durationSec><programId>717480857</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600798</productId><useBeginTime>2021-11-07 13:22:33</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频体育赛事按次18元</productName><flowKB>69668</flowKB><programName>UFC第268期 （郑文祺、尚志法、娄一晨、张光宇、盛宇）</programName><durationSec>1227</durationSec><programId>717480857</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600798</productId><useBeginTime>2021-11-07 12:53:25</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频体育赛事按次18元</productName><flowKB>15348</flowKB><programName>UFC第268期 （郑文祺、尚志法、娄一晨、张光宇、盛宇）</programName><durationSec>541</durationSec><programId>717480857</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600798</productId><useBeginTime>2021-11-07 12:53:19</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频体育赛事按次18元</productName><flowKB>47615</flowKB><programName>UFC第268期 （郑文祺、尚志法、娄一晨、张光宇、盛宇）</programName><durationSec>559</durationSec><programId>717480857</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600798</productId><useBeginTime>2021-11-07 11:00:01</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频体育赛事按次18元</productName><flowKB>2942</flowKB><programName>UFC第268期 （郑文祺、尚志法、娄一晨、张光宇、盛宇）</programName><durationSec>5931</durationSec><programId>717480857</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600798</productId><useBeginTime>2021-11-07 10:59:43</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频体育赛事按次18元</productName><flowKB>4663</flowKB><programName>UFC第268期 （郑文祺、尚志法、娄一晨、张光宇、盛宇）</programName><durationSec>2399</durationSec><programId>717480857</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600798</productId><useBeginTime>2021-11-07 10:58:49</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频体育赛事按次18元</productName><flowKB>495510</flowKB><programName>UFC第268期 （郑文祺、尚志法、娄一晨、张光宇、盛宇）</programName><durationSec>6274</durationSec><programId>717480857</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600798</productId><useBeginTime>2021-11-07 10:58:48</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频体育赛事按次18元</productName><flowKB>98680</flowKB><programName>UFC第268期 （郑文祺、尚志法、娄一晨、张光宇、盛宇）</programName><durationSec>6122</durationSec><programId>717480857</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-07 10:52:31</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>1823</flowKB><programName>张伟丽VS罗斯终极预告：马格南子弹上膛枪指玫瑰</programName><durationSec>5</durationSec><programId>717502562</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 09:48:35</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>4869</flowKB><programName>张伟丽VS罗斯一番战深度回顾：暴徒玫瑰再度加冕UFC金腰带</programName><durationSec>12</durationSec><programId>716943244</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 09:48:35</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>1405</flowKB><programName>张伟丽VS罗斯一番战深度回顾：暴徒玫瑰再度加冕UFC金腰带</programName><durationSec>4</durationSec><programId>716943244</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 09:48:07</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>2906</flowKB><programName>UFC数字赛第267期主赛全场回放（张凌宇、张光宇、尤纳斯）</programName><durationSec>1</durationSec><programId>717169966</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 09:47:59</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>4154</flowKB><programName>UFC数字赛第267期主赛全场回放（张凌宇、张光宇、尤纳斯）</programName><durationSec>3</durationSec><programId>717169966</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 09:40:15</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>1645</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>240</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:55:57</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>13431</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>3084</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:55:36</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>47868</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>3135</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:54:56</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>75092</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>3176</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:54:54</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>341220</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>3177</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:54:09</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>2827</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>4</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:54:09</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>0</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>1</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:54:09</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>947</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>3</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:54:07</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>944</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>0</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:54:06</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>947</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>1</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:54:05</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>2827</flowKB><programName>UFC白大拿挑战者系列赛第五季第十周（张光宇）</programName><durationSec>5</durationSec><programId>717270598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:53:54</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>1413</flowKB><programName>东契奇外线穿花送妙传 小哈达威超远三分一箭穿心</programName><durationSec>2</durationSec><programId>717320771</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-11-03 08:53:54</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>1</flowKB><programName>东契奇外线穿花送妙传 小哈达威超远三分一箭穿心</programName><durationSec>1</durationSec><programId>717320771</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 03:25:23</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>8010</flowKB><programName>UFC三位中国选手首秀，中国力量特效制作，次元爆炸超“刺激”</programName><durationSec>2</durationSec><programId>709562279</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 03:24:41</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>20163</flowKB><programName>苏木达尔基赛前采访：最好的生日愿望就是带着胜利离开</programName><durationSec>92</durationSec><programId>701028352</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 03:21:51</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>3433</flowKB><programName>UFC 267 PPV Blachowicz vs. Teixeira（张凌宇、张光宇、尤纳斯）</programName><durationSec>9</durationSec><programId>717076715</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 03:20:20</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>10763</flowKB><programName>张伟丽：我希望很快和罗斯打二番战 还有意识裁判就吹停了比赛</programName><durationSec>55</durationSec><programId>709458047</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 03:20:03</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>6</flowKB><programName>李景亮身披国旗亮相称重 与奇马耶夫对视剑拔弩张</programName><durationSec>1</durationSec><programId>717121581</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 03:18:18</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>0</flowKB><programName>UFC 267 PPV Blachowicz vs. Teixeira（张凌宇、张光宇、尤纳斯）</programName><durationSec>1</durationSec><programId>717076715</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 03:18:18</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>4497</flowKB><programName>UFC 267 PPV Blachowicz vs. Teixeira（张凌宇、张光宇、尤纳斯）</programName><durationSec>19</durationSec><programId>717076715</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:49:55</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>386413</flowKB><programName>UFC 267 PPV Blachowicz vs. Teixeira（张凌宇、张光宇、尤纳斯）</programName><durationSec>4133</durationSec><programId>717076715</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:49:55</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>3040</flowKB><programName>UFC 267 PPV Blachowicz vs. Teixeira（张凌宇、张光宇、尤纳斯）</programName><durationSec>3662</durationSec><programId>717076715</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:49:55</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>33892</flowKB><programName>UFC 267 PPV Blachowicz vs. Teixeira（张凌宇、张光宇、尤纳斯）</programName><durationSec>4134</durationSec><programId>717076715</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:25:41</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>3</flowKB><programName>21/22赛季西甲第12轮 瓦伦西亚 vs 比利亚雷亚尔（张昊泽）</programName><durationSec>0</durationSec><programId>717061609</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:20:15</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>9</flowKB><programName>21/22赛季西甲第12轮 瓦伦西亚 vs 比利亚雷亚尔（张昊泽）</programName><durationSec>78</durationSec><programId>717061609</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:16:03</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>19013</flowKB><programName>21/22赛季西甲第12轮 瓦伦西亚 vs 比利亚雷亚尔（张昊泽）</programName><durationSec>618</durationSec><programId>717061609</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:16:03</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>37686</flowKB><programName>21/22赛季西甲第12轮 瓦伦西亚 vs 比利亚雷亚尔（张昊泽）</programName><durationSec>618</durationSec><programId>717061609</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:15:04</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>5528</flowKB><programName>共舞未来！动感地带2021中国街舞联赛校园赛火热开赛</programName><durationSec>4</durationSec><programId>716558598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:14:30</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>5543</flowKB><programName>新生无畏！locking就现在，我的舞台听我的</programName><durationSec>11</durationSec><programId>716557572</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:14:12</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>13172</flowKB><programName>共舞未来！动感地带2021中国街舞联赛校园赛火热开赛</programName><durationSec>9</durationSec><programId>716558598</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:12:58</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>20234</flowKB><programName>必须全场最佳！穆勒晃过防守球员兜远角得手</programName><durationSec>27</durationSec><programId>717157741</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:09:45</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>808</flowKB><programName>UFC 267 PPV Blachowicz vs. Teixeira（张凌宇、张光宇、尤纳斯）</programName><durationSec>55</durationSec><programId>717076715</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-31 01:09:45</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>14188</flowKB><programName>UFC 267 PPV Blachowicz vs. Teixeira（张凌宇、张光宇、尤纳斯）</programName><durationSec>97</durationSec><programId>717076715</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-29 00:56:15</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>16842</flowKB><programName>刺激肾上腺素！张伟丽暴力输出，战斗力爆表</programName><durationSec>2</durationSec><programId>708605874</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-29 00:54:43</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>27432</flowKB><programName>21/22赛季NBA常规赛全场集锦：灰熊96:116开拓者（20211028）</programName><durationSec>158</durationSec><programId>717052865</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-29 00:54:43</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>4702</flowKB><programName>张伟丽VS罗斯一番战深度回顾：暴徒玫瑰再度加冕UFC金腰带</programName><durationSec>5</durationSec><programId>716943244</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-29 00:54:07</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>9458</flowKB><programName>李景亮赛前采访：对手想吃掉我？我还想喝他的血呢</programName><durationSec>13</durationSec><programId>717031972</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-29 00:50:58</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>22525</flowKB><programName>想踩着我上位？李景亮：问我的拳头答应不答应</programName><durationSec>151</durationSec><programId>717077173</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-29 00:49:50</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>4972</flowKB><programName>李景亮赛前采访：对手想吃掉我？我还想喝他的血呢</programName><durationSec>9</durationSec><programId>717031972</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-29 00:49:49</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>1329</flowKB><programName>李景亮赛前采访：对手想吃掉我？我还想喝他的血呢</programName><durationSec>1</durationSec><programId>717031972</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-29 00:47:43</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>18754</flowKB><programName>21/22赛季西甲前瞻第11轮：武磊战毕巴望救赎巴萨碰弱旅欲止颓</programName><durationSec>122</durationSec><programId>716979926</programId><channelId>0116_2500090610-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-25 00:25:19</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>6411</flowKB><programName>21/22赛季CBA常规赛第4轮半场集锦：上海vs广东 </programName><durationSec>21</durationSec><programId>716923469</programId><channelId>0116_2500090500-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-25 00:22:02</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>2465</flowKB><programName>21/22赛季西甲第10轮：巴塞罗那vs皇家马德里（梁祥宇、李欣、刘畅）</programName><durationSec>149</durationSec><programId>716836326</programId><channelId>0116_2500090500-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-25 00:21:20</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>22618</flowKB><programName>21/22赛季西甲第10轮：巴塞罗那vs皇家马德里（梁祥宇、李欣、刘畅）</programName><durationSec>222</durationSec><programId>716836326</programId><channelId>0116_2500090500-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-10-25 00:19:53</useBeginTime><netTypeName>WLAN</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>1267</flowKB><programName>21/22赛季西甲第10轮：巴塞罗那vs皇家马德里（梁祥宇、李欣、刘畅）</programName><durationSec>12</durationSec><programId>716836326</programId><channelId>0116_2500090500-99000-200300280000000</channelId><broadcastTypeName>直播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-07-16 09:28:04</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>2180</flowKB><programName>精武战士</programName><durationSec>96</durationSec><programId>659883342</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-07-16 09:26:06</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>17499</flowKB><programName>精武战士</programName><durationSec>589</durationSec><programId>659883342</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>未知</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-07-16 09:26:04</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>47923</flowKB><programName>精武战士</programName><durationSec>592</durationSec><programId>659883342</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600496</productId><useBeginTime>2021-07-16 09:20:14</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频按次计费5元</productName><flowKB>181</flowKB><programName>风平浪静</programName><durationSec>1</durationSec><programId>697231860</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600496</productId><useBeginTime>2021-07-16 09:20:13</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频按次计费5元</productName><flowKB>442</flowKB><programName>风平浪静</programName><durationSec>0</durationSec><programId>697231860</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-07-16 09:20:13</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>326</flowKB><programName>谍影特工</programName><durationSec>0</durationSec><programId>676809915</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600496</productId><useBeginTime>2021-07-16 09:18:22</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频按次计费5元</productName><flowKB>0</flowKB><programName>风平浪静</programName><durationSec>0</durationSec><programId>697231860</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-07-16 09:18:22</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>291</flowKB><programName>谍影特工</programName><durationSec>0</durationSec><programId>676809915</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028600496</productId><useBeginTime>2021-07-16 09:18:22</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频按次计费5元</productName><flowKB>881</flowKB><programName>风平浪静</programName><durationSec>0</durationSec><programId>697231860</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data><data><chargeTypeName>按次</chargeTypeName><provName>西藏</provName><productId>2028597139</productId><useBeginTime>2021-07-16 09:18:21</useBeginTime><netTypeName>4G</netTypeName><termProdName>咪咕视频手机客户端5_0_安卓版</termProdName><productName>咪咕视频-收费业务0元按次</productName><flowKB>51564</flowKB><programName>遇见你真好</programName><durationSec>31</durationSec><programId>676823965</programId><channelId>0116_2500080930-99000-200300280000000</channelId><broadcastTypeName>点播</broadcastTypeName></data></datalist></body></CustomServiceInterfaceResponse>";
//        DataBasePo dataPo = XmlUtils.xmlToBean(xmlStr, DataBasePo.class);
//        System.out.println(dataPo);

//        List<String> allHours = DateUtils.getAllHours("2021123100", "2022010223");
//        System.out.println(allHours);
//        String str = "{\"a\":\"1\",\"b\":\"2\"}";
//
//        String s = "1";
//        HashMap<String, String> map = JSONObject.parseObject(str, HashMap.class);
//        Object b =  map.get("a");
//        System.out.println(b);
//        System.out.println(map.get("a"));
//        System.out.println(s.equals(b));

//        System.out.println(String.format("ss%s",2));

//        String s = "{\"createTime\":\"\",\"userName\":\"fengf\"}";
//
//        UserPO userPO = JSONObject.parseObject(s, UserPO.class);
//        System.out.println(userPO);
//
//        List<Integer> list = Arrays.asList(1,2,3,4,5);
//
//        System.out.println(JSONObject.toJSON(list));
//
//        Collections.shuffle(list);
//        System.out.println(list);
//        Random random = new Random();
//        for(int i=0; i<5; i++){
//            int num = random.nextInt(100);
//            System.out.println(num);
//        }
//        LocalTime localTime = LocalTime.of(9,0);
//        LocalTime localTime1 = LocalTime.of(22,0);
//
//        System.out.println(localTime);
//        System.out.println(localTime1);
//        System.out.println(localTime.isAfter(localTime1));

//        List<String> list = Arrays.asList("1" ,"2","3");
//
//
//        System.out.println(list);
//
//        List<UserPO> collect = list.stream().map(s -> {
//            UserPO userPO = new UserPO();
//            userPO.setUserName(s);
//            return userPO;
//        }).collect(Collectors.toList());
//        System.out.println(collect);
//
//        List<List<String>> list1 = Arrays.asList(Arrays.asList("1",null), Arrays.asList("2"), Arrays.asList("3"));
//        System.out.println(list1);
//
//        TreeSet<UserPO> collect1 = list1.stream().flatMap(Collection::stream).map(s -> {
//            UserPO userPO = new UserPO();
//            userPO.setUserName(s);
//            return userPO;
//        }).collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserPO::getUserName, Comparator.nullsLast(Comparator.reverseOrder())))));
//        System.out.println(collect1);
//
//        TreeSet<UserPO> collect2 = list1.stream().flatMap(Collection::stream).map(s -> {
//            UserPO userPO = new UserPO();
//            userPO.setUserName(s);
//            return userPO;
//        }).collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserPO::getUserName, Comparator.nullsFirst(Comparator.naturalOrder())))));
//        System.out.println(collect2);

//        //设置各个参数的值，并放入map
//        String md5str = "";
//        JSONObject map = new JSONObject();
//        map.put("appid","1531000001");
//        map.put("channelId","1531");
//        map.put("msisdn","18059881776");
//        map.put("seq","1010142238111");
//        map.put("servicetype","5");
//        map.put("usertype","10");
//        map.put("userId","90000001210");
//        String baseKey = "382085DF8C1D9F3DEC49B31FF4173E55";
//
//        List<String> list = new ArrayList<String>();
//// 对所有参数进行排序
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            list.add(entry.getKey());
//        }
//        Collections.sort(list);
//// 拼接要加密的字符串
//        for (String key : list) {
//            md5str += key + map.get(key);
//        }
//
////MD5加密，得到hash值
//        System.out.println("加密前：" + md5str.trim() + baseKey);
//        String hash = org.apache.commons.codec.digest.DigestUtils.md5Hex(md5str.trim() + baseKey);
//        System.out.println(hash);

        Date offsetDate = DateUtils.getOffsetDate(new Date(), -2);
        System.out.println(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, offsetDate));


    }

    @Test
    public void testInterface() {
        UserPO po = iUser.selectUserByUserName("op");
        System.out.println(po);
    }

}
