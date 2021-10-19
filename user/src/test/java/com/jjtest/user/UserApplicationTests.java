package com.jjtest.user;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jjtest.tool.redis.DistributedLock;
import com.jjtest.tool.util.DeleteFileUtil;
import com.jjtest.tool.util.KeyWordLuceneService;
import com.jjtest.tool.util.sftp.FtpSitePo;
import com.jjtest.tool.util.sftp.SFtpUtil;
import com.jjtest.tool.util.SpringUtils;
import com.jjtest.tool.util.sftp.SftpChannelInfo;
import com.jjtest.tool.util.threadpool.AsyncInvoke;
import com.jjtest.user.config.TestConfig;
import com.jjtest.user.dao.UserMapper;
import com.jjtest.user.po.BasePo;
import com.jjtest.user.po.StudentPo;
import com.jjtest.user.po.UserPO;
import com.jjtest.user.service.*;
import com.jjtest.user.service.eat.factory.AnimalFactory;
import com.jjtest.user.util.RedisUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
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
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i=0; i<10; i++) {
            asyncInvoke.submit(()->{

                boolean jjkey = distributedLock.tryLock("jjkey", "ff",50000, 5, 1100);
                System.out.println(jjkey);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distributedLock.releaseDistributedLock("jjkey","ff");
                countDownLatch.countDown();
            });
        }

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
                folder.mkdirs();
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

        String str = "200223,200344,433434";
        System.out.println(str.contains("200234"));

    }

    @Test
    public void testInterface() {
        UserPO po = iUser.selectUserByUserName("op");
        System.out.println(po);
    }

}
