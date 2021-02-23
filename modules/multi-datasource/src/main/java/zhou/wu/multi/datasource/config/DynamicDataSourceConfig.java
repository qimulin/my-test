package zhou.wu.multi.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lin.xc
 * @create: 2021-2-23
 **/
@Configuration
public class DynamicDataSourceConfig {

    /**
     * 系统库
     * */
    @Bean
    @ConfigurationProperties("spring.datasource.system.druid")
    public DataSource systemDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 目标库
     * 【注意】若要支持读个目标库，这样写不大好，最好能动态实现加载，但时间有限，有时间再研究下那种比较优雅的写法
     * */
    @Bean
    @ConfigurationProperties("spring.datasource.target.druid")
    public DataSource targetDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(
            DataSource systemDataSource,
            DataSource targetDataSource
    ){
        // 构造DynamicDataSource，并注入Spring
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(KEY_SYSTEM, systemDataSource);
//        targetDataSources.put(KEY_TARGET, targetDataSource);
        return new DynamicDataSource(systemDataSource, targetDataSource);
    }
}
