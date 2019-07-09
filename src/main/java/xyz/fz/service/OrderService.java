package xyz.fz.service;

import xyz.fz.api.Order;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface OrderService {
    void create(BigInteger userId, Integer productId, Integer productCount);

    void createAtTwoDs(BigInteger userId, Integer productId, Integer productCount);

    void batchOrder(BigInteger userId, List<Map> products);

    List<Order> listLast10();
}
