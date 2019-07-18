package xyz.fz.test;

import io.shardingsphere.api.HintManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import xyz.fz.Application;
import xyz.fz.api.Order;
import xyz.fz.dao.CommonDao;
import xyz.fz.entity.OrderEntity;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {Application.class})
@ActiveProfiles("sharding2")
public class HintTest {
    @Resource
    private CommonDao db;

    @Test
    @Transactional
    public void notHint() {
        List<Order> list = db.queryListBySql("select order_id as orderId from t_order limit 10", null, Order.class);
        Assert.assertEquals(0, list.size());
        OrderEntity order = new OrderEntity();
        order.setUserId(22L);
        order.setCreateTime(new Date());
        db.save(order);
        // private boolean isMasterRoute(final SQLType sqlType) {
        //     return SQLType.DQL != sqlType || MasterVisitedManager.isMasterVisited() || HintManagerHolder.isMasterRouteOnly();
        // }

        // MasterVisitedManager.isMasterVisited()
        // 当master库被访问后，后续的sql将使用master库进行访问，故第二次的查询将从master库查询出10调结果
        List<Order> list2 = db.queryListBySql("select order_id as orderId from t_order limit 10", null, Order.class);
        Assert.assertEquals(10, list2.size());
    }

    @Test
    @Transactional
    public void hint() {
        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();
        List<Order> list = db.queryListBySql("select order_id as orderId from t_order limit 10", null, Order.class);
        Assert.assertNotEquals(0, list.size());
        OrderEntity order = new OrderEntity();
        order.setUserId(22L);
        order.setCreateTime(new Date());
        db.save(order);
    }
}
