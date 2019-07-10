package xyz.fz.configuration;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Data
@Configuration
public class ShardingJdbcConfiguration implements InitializingBean {

    @Resource
    private ShardingJdbcProperties shardingJdbcProperties;

    @Bean
    public DefaultKeyGenerator defaultKeyGenerator() {
        return new DefaultKeyGenerator();
    }

    @Override
    public void afterPropertiesSet() {
        DefaultKeyGenerator.setWorkerId(shardingJdbcProperties.getSnowflakeWorkerId());
    }
}
