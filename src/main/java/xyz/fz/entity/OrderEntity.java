package xyz.fz.entity;

import xyz.fz.api.Order;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_order")
public final class OrderEntity extends Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @Override
    public Long getOrderId() {
        return super.getOrderId();
    }

    @Column(name = "user_id")
    @Override
    public Long getUserId() {
        return super.getUserId();
    }

    @Column(name = "create_time")
    @Override
    public Date getCreateTime() {
        return super.getCreateTime();
    }
}
