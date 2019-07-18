package xyz.fz.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fz.api.Order;
import xyz.fz.dao.CommonDao;
import xyz.fz.entity.OrderEntity;
import xyz.fz.entity.OrderItemEntity;
import xyz.fz.service.OrderService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private CommonDao db;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Long userId, Integer productId, Integer productCount) {
        OrderEntity orderEntity = createOrder(userId);
        db.save(orderEntity);
        db.save(createOrderItem(userId, orderEntity.getOrderId(), productId, productCount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createAtTwoDs(Long userId, Integer productId, Integer productCount) {
        OrderEntity orderEntity = createOrder(userId);
        db.save(orderEntity);
        db.save(createOrderItem(userId, orderEntity.getOrderId(), productId, productCount));

        Long userId2 = userId + 1L;
        OrderEntity orderEntity2 = createOrder(userId2);
        db.save(orderEntity2);
        db.save(createOrderItem(userId2, orderEntity2.getOrderId(), productId, productCount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchOrder(Long userId, List<Map> products) {
        List<OrderItemEntity> list = new ArrayList<>();
        OrderEntity orderEntity = createOrder(userId);
        db.save(orderEntity);
        for (Map product : products) {
            list.add(
                    createOrderItem(
                            userId,
                            orderEntity.getOrderId(),
                            Integer.parseInt(product.get("productId").toString()),
                            Integer.parseInt(product.get("productCount").toString())
                    )
            );
        }
        db.batchSave(list);
    }

    @Override
    public List<Order> listLast10() {
        return db.queryListBySql("select order_id as orderId, user_id as userId, create_time as createTime from t_order order by create_time desc limit 10", null, Order.class);
    }

    private OrderEntity createOrder(Long userId) {
        // System.out.println("create orderEntity userId: " + userId);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(userId);
        orderEntity.setCreateTime(new Date());
        return orderEntity;
    }

    private OrderItemEntity createOrderItem(Long userId, Long orderId, Integer productId, Integer productCount) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setOrderId(orderId);
        orderItemEntity.setUserId(userId);
        orderItemEntity.setProductId(productId);
        orderItemEntity.setProductCount(productCount);
        return orderItemEntity;
    }
}
