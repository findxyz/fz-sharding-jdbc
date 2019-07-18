package xyz.fz.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class Config implements Serializable {
    private Long id;

    private String keyword;

    private String value;
}
