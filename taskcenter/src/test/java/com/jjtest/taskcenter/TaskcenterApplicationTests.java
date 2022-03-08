package com.jjtest.taskcenter;

import com.jjtest.taskcenter.client.UserClient;
import com.jjtest.taskcenter.constant.TaskServiceEnum;
import com.jjtest.taskcenter.dao.product.ProductDao;
import com.jjtest.taskcenter.dao.user.UserDao;
import com.jjtest.taskcenter.po.NodePO;
import com.jjtest.taskcenter.po.UserPO;
import com.jjtest.taskcenter.po.WorkTaskItemPO;
import com.jjtest.taskcenter.service.KafkaProducer;
import com.jjtest.taskcenter.service.ProductService;
import com.jjtest.taskcenter.task.base.TaskHander;
import com.jjtest.taskcenter.task.service.UserCleanService;
import com.jjtest.tool.response.ResultObject;
import com.jjtest.tool.util.DateUtils;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private TaskHander taskHander;

    @Autowired
    private UserCleanService userCleanService;

    @Test
    public void contextLoads() {
//        UserPO po = new UserPO();
//        po.setUserName("jj");
//        UserPO userPO = userDao.selectUser(po);
//        System.out.println(userPO);
//
//        ProductPO productPO = productDao.selectById(1L);
//        System.out.println(productPO);

        userCleanService.clean();
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
//        kafkaProducer.send("你好");
        try {
            taskHander.doTask(TaskServiceEnum.CANCEL_ORDER_TASK.getTaskId(), "test",true, true);

            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

//        try {
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            calendar.setTime(simpleDateFormat.parse("2008-02-29"));
//            System.out.println(simpleDateFormat.format(calendar.getTime()));
//
//            calendar.add(Calendar.YEAR, -1);
//            System.out.println(simpleDateFormat.format(calendar.getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        List<String> list = new ArrayList<>();
//        list.add("2222");
//        list.add("3");
//        list.add("1");
//        list.add("5");
//        list.add("2");
//        List<Integer> collect = list.stream().map(Integer::parseInt).collect(Collectors.toList());
//        System.out.println(collect);
//        System.out.println(collect.contains(2222));
//
//        list.sort(Comparator.comparing(Integer::parseInt));
//        System.out.println(list);
//
//        List<Integer> num = new ArrayList<>();
//        num.add(new Integer(2));
//        num.add(99999999);
//        System.out.println(num);
//        num.remove(new Integer(99999999));
//        System.out.println(num);
        UserPO userPO = new UserPO(1,"jj");
        UserPO userPO1 = Optional.ofNullable(userPO).orElseGet(()->{return new UserPO(2,"ff");});
        System.out.println(userPO1);

        DateUtils.


    }

    private Map<Long, List<WorkTaskItemPO>> getNextItem(List<WorkTaskItemPO> list) {
        Map<Long, List<WorkTaskItemPO>> result = new HashMap<>();
        if(CollectionUtils.isEmpty(list)){
            return result;
        }
        list.forEach(item -> {
            List<WorkTaskItemPO> nextList = list.stream().filter((po) -> {
                return item.getId().equals(po.getPreId());
            }).collect(Collectors.toList());
            result.put(item.getId(), nextList);
        });

        return result;
    }

    private List<NodePO>  getAllRoute(List<WorkTaskItemPO> list) {
        List<NodePO> result = new ArrayList<>();
        result.add(new NodePO(true, list.get(0).getId() , null, null));
        this.getRoute(list.get(0), this.getNextItem(list), result);
        return result;
    }

    private void getRoute(WorkTaskItemPO item, Map<Long, List<WorkTaskItemPO>> nextItemMap, List<NodePO> result) {
        List<WorkTaskItemPO> nextItem = nextItemMap.get(item.getId());
        //多派
        List<WorkTaskItemPO> isMore = nextItem.stream().filter(po -> {
            return po.isMore() == true;
        }).collect(Collectors.toList());
        //追单
        List<WorkTaskItemPO> addItem = nextItem.stream().filter(po -> {
            return po.getStatus() == 4;
        }).collect(Collectors.toList());
        //正常
        List<WorkTaskItemPO> notMore = nextItem.stream().filter(po -> {
            return po.getStatus() != 4 && po.isMore() == false;
        }).collect(Collectors.toList());

        List<NodePO> temp = new ArrayList<>();
        result.forEach(line -> {
            temp.add(line);
        });
        for (NodePO routePO : temp) {
            if (CollectionUtils.isEmpty(nextItem)) {
                continue;
            }
            nextItem.forEach(next -> {
                if(routePO.isHead()){
                    NodePO nodePO = new NodePO(false, next.getId(), routePO.getId(), null);
                    nodePO.setPreNode(routePO);
                    nodePO.setPreId(routePO.getId());
                    nodePO.setT(routePO);
                    routePO.setNextNode(nodePO);
                    routePO.setNextId(next.getId());
                } else if(next.getPreId().equals(routePO.getId())){
                    NodePO nodePO = new NodePO(false, next.getId(), routePO.getId(), null);
                    nodePO.setPreNode(routePO);
                    nodePO.setPreId(routePO.getId());
                    nodePO.setT(routePO.getT());
                    routePO.setNextNode(nodePO);
                    routePO.setNextId(next.getId());
                }

            });
            if (nextItem.size() == 1){
                this.getRoute(nextItem.get(0), nextItemMap, result);
                continue;
            }
            //追加
            addItem.forEach(addItemPo->{
                this.getRoute(addItemPo, nextItemMap, result);
            });

            //多派
            int i = 1;
            for (WorkTaskItemPO workTaskItemPO : isMore) {
                if (i == 1) {
                    i++;
                    this.getRoute(workTaskItemPO, nextItemMap, result);
                    continue;
                }
                //添加线路
                result.add(routePO.getT());
                this.getRoute(workTaskItemPO, nextItemMap, result);
            }
            if(CollectionUtils.isNotEmpty(notMore)) {
                this.getRoute(notMore.get(0), nextItemMap, result);
            }
        }
    }

    @Test
    public void routeTest() {
        List<WorkTaskItemPO> list = new ArrayList<>();
        WorkTaskItemPO po = new WorkTaskItemPO(1L, 2, null, false);
        list.add(po);
        list.add(new WorkTaskItemPO(2L, 2, 1L, false));
        list.add(new WorkTaskItemPO(3L, 2, 1L, false));

        list.add(new WorkTaskItemPO(4L, 2, 3L, true));
        list.add(new WorkTaskItemPO(5L, 2, 3L, true));

        list.add(new WorkTaskItemPO(6L, 2, 4L, false));
        list.add(new WorkTaskItemPO(7L, 2, 5L, false));
        list.add(new WorkTaskItemPO(8L, 2, 7L, false));
        List<NodePO> allRoute = this.getAllRoute(list);
        allRoute.forEach(r -> {
            System.out.println(r);

        });


    }

    @Data
    class RoutePO implements Cloneable{
        private List<Long> route;

        @Override
        protected RoutePO clone() throws CloneNotSupportedException {
            return (RoutePO) super.clone();
        }
    }

}
