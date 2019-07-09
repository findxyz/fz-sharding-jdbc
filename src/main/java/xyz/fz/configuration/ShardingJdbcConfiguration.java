package xyz.fz.configuration;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShardingJdbcConfiguration implements InitializingBean {
    @Value("${snowflake.worker.id}")
    private long workerId;

    @Bean
    public DefaultKeyGenerator defaultKeyGenerator() {
        return new DefaultKeyGenerator();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultKeyGenerator.setWorkerId(workerId);
    }
}
