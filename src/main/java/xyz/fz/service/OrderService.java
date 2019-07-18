package xyz.fz.service;

import xyz.fz.api.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void create(Long userId, Integer productId, Integer productCount);

    void createAtTwoDs(Long userId, Integer productId, Integer productCount);

    void batchOrder(Long userId, List<Map> products);

    List<Order> listLast10();
}
