package xyz.fz.entity;

import xyz.fz.api.OrderItem;

import javax.persistence.*;

@Entity
@Table(name = "t_order_item")
public final class OrderItemEntity extends OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    @Override
    public Long getOrderItemId() {
        return super.getOrderItemId();
    }

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

    @Column(name = "product_id")
    @Override
    public Integer getProductId() {
        return super.getProductId();
    }

    @Column(name = "product_count")
    @Override
    public Integer getProductCount() {
        return super.getProductCount();
    }
}
