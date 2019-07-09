package xyz.fz.api;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class Config implements Serializable {
    private BigInteger id;

    private String keyword;

    private String value;
}
