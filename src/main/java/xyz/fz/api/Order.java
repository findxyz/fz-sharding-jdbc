package xyz.fz.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
public class Order implements Serializable {
    private BigInteger orderId;

    private BigInteger userId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
}
