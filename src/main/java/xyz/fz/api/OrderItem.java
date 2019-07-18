package xyz.fz.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItem implements Serializable {
    private Long orderItemId;

    private Long orderId;

    private Long userId;

    private Integer productId;

    private Integer productCount;
}
