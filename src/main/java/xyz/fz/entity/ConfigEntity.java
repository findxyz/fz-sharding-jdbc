package xyz.fz.entity;

import xyz.fz.api.Config;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "t_config")
public final class ConfigEntity extends Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Override
    public BigInteger getId() {
        return super.getId();
    }

    @Column(name = "keyword")
    @Override
    public String getKeyword() {
        return super.getKeyword();
    }

    @Column(name = "value")
    @Override
    public String getValue() {
        return super.getValue();
    }
}
