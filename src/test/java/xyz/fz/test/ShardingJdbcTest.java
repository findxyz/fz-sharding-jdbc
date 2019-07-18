package xyz.fz.test;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.fz.Application;
import xyz.fz.api.OrderItem;
import xyz.fz.dao.CommonDao;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {Application.class})
public class ShardingJdbcTest {
    @Resource
    private CommonDao db;

    @Test
    public void testOrderItemList() {
        String sql = "";
        sql += "SELECT ";
        sql += "order_item_id AS orderItemId, ";
        sql += "order_id AS orderId, ";
        sql += "user_id AS userId, ";
        sql += "product_id AS productId, ";
        sql += "product_count AS productCount ";
        sql += "FROM ";
        sql += "t_order_item ";
        sql += "ORDER BY ";
        sql += "order_item_id DESC ";
        sql += "LIMIT 10 ";
        List<OrderItem> list = db.queryListBySql(sql, null, OrderItem.class);
        System.out.println(list);
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void getUserOrderList() {
        String sql = "";
        sql += "SELECT ";
        sql += "o.order_id AS orderId, ";
        sql += "o.user_id AS userId, ";
        sql += "o.create_time AS createTime, ";
        sql += "i.order_item_id AS orderItemId, ";
        sql += "i.product_id AS productId, ";
        sql += "i.product_count AS productCount ";
        sql += "FROM ";
        sql += "t_order o ";
        sql += "INNER JOIN t_order_item i ON o.order_id = i.order_id ";
        sql += "WHERE ";
        sql += "1 = 1 ";
        sql += "AND o.user_id = :userId ";
        sql += "ORDER BY ";
        sql += "o.create_time DESC ";
        sql += "LIMIT 100 ";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", 1562661695648L);
        List<Map> list = db.queryListBySql(sql, params, Map.class);
        System.out.println(list);
    }

    @Test
    public void getOrderListByOrderIds() {
        String sql = "";
        sql += "SELECT ";
        sql += "o.order_id AS orderId, ";
        sql += "o.user_id AS userId, ";
        sql += "i.order_item_id ";
        sql += "FROM ";
        sql += "t_order o ";
        sql += "INNER JOIN t_order_item i ON o.order_id = i.order_id ";
        sql += "WHERE ";
        sql += "1 = 1 ";
        sql += "AND o.order_id IN :orderIds ";
        sql += "AND o.user_id = :userId ";
        sql += "ORDER BY ";
        sql += "o.create_time DESC ";
        sql += "LIMIT 100 ";
        Map<String, Object> params = new HashMap<>();
        params.put("orderIds", Lists.newArrayList(355392167706886147L, 355392167706886148L));
        params.put("userId", 1562661695648L);
        List<Map> list = db.queryListBySql(sql, params, Map.class);
        System.out.println(list);
    }
}
