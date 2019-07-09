package xyz.fz.api;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class OrderItem implements Serializable {
    private BigInteger orderItemId;

    private BigInteger orderId;

    private BigInteger userId;

    private Integer productId;

    private Integer productCount;
}
