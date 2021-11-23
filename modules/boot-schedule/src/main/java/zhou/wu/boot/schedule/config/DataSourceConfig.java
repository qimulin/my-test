package zhou.wu.boot.schedule.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lin.xc
 * @create: 2021-2-23
 **/
@Configuration
public class DataSourceConfig {

    private static final String PREFIX_SYSTEM_DS = "spring.datasource.druid";

    /**
     * 系统库
     * */
    @Bean
    @ConfigurationProperties(PREFIX_SYSTEM_DS)
    public DruidDataSource dataSource(){
        DruidDataSource ds = DruidDataSourceBuilder.create().build();
        return ds;
    }

}
