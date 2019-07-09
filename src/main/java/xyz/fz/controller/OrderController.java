package xyz.fz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fz.api.Order;
import xyz.fz.service.OrderService;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/order")
@SuppressWarnings("unchecked")
public class OrderController {
    @Resource
    private OrderService orderService;

    @RequestMapping("/create")
    public String create() {
        Random random = new Random(9999L);
        orderService.create(BigInteger.valueOf(System.currentTimeMillis()), random.nextInt(100), random.nextInt(10));
        return "create ok";
    }

    @RequestMapping("/createAtTwoDs")
    public String createAtTwoDs() {
        Random random = new Random(9999L);
        orderService.createAtTwoDs(BigInteger.valueOf(System.currentTimeMillis()), random.nextInt(100), random.nextInt(10));
        return "create at two ds ok";
    }

    @RequestMapping("/batchCreate")
    public String batchCreate() {
        List<Map> list = new ArrayList<>();
        list.add(randomProduct());
        list.add(randomProduct());
        list.add(randomProduct());
        list.add(randomProduct());
        orderService.batchOrder(BigInteger.valueOf(System.currentTimeMillis()), list);
        return "batch create ok";
    }

    @RequestMapping("/list")
    public List<Order> list() {
        return orderService.listLast10();
    }

    private Map randomProduct() {
        Random random = new Random(9999L);
        Map product = new HashMap();
        product.put("productId", random.nextInt(100));
        product.put("productCount", random.nextInt(10));
        return product;
    }
}
