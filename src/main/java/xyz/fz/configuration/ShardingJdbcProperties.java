package xyz.fz.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sharding.jdbc.config")
public class ShardingJdbcProperties {
    private long snowflakeWorkerId;
}
