package zhou.wu.multi.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.aliyun.odps.Odps;
import com.aliyun.odps.jdbc.OdpsConnection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

import static zhou.wu.multi.datasource.config.DynamicDataSourceConstant.*;
/**
 * @author: lin.xc
 * @create: 2021-2-23
 **/
@Configuration
public class DynamicDataSourceConfig {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 系统库
     * */
    @Bean(DS_SYSTEM)
    @ConfigurationProperties(PREFIX_SYSTEM_DS_DRUID)
    public DruidDataSource systemDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 目标库
     * 【注意】若要支持读个目标库，这样写不大好，最好能动态实现加载，但时间有限，有时间再研究下那种比较优雅的写法
     * */
    @Bean(DS_TARGET)
    @ConfigurationProperties(PREFIX_TARGET_DS_DRUID)
    public DruidDataSource targetDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 留工优工库
     * */
    @Bean(DS_LGYG)
    @ConditionalOnProperty(
            name= PREFIX_LGYG_DS_DRUID+".driver-class-name",
            havingValue = TargetDataSourceSupport.Driver.MYSQL)
    @ConfigurationProperties(PREFIX_LGYG_DS_DRUID)
    public DruidDataSource lgygDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(){
        Map<String, DruidDataSource> dsBeans = applicationContext.getBeansOfType(DruidDataSource.class);
        // 构造DynamicDataSource，并注入Spring
        DynamicDataSource dynamicDataSource = new DynamicDataSource(dsBeans.get(DS_SYSTEM));
        // 循环将数据源加入
        for (Map.Entry<String, DruidDataSource> entry:dsBeans.entrySet()) {
            dynamicDataSource.addDatasource(entry.getKey(), entry.getValue());
        }
        dynamicDataSource.init();
        return dynamicDataSource;
    }

    /**
     * 必需有目标数据源的Bean，且为Odps
     * 【说明】：系统中存在需要查询数据源中表和字段的元数据信息，但是Odps数据源并不如Mysql那样访问information_schema方便，详见阿里云
     * TODO：才疏学浅，目前只能想到这样比较简单，有更优雅的写法，欢迎来改
     * */
    @Bean
    @SneakyThrows
    @ConditionalOnBean(name=DS_TARGET)
    @ConditionalOnProperty(
            name = PRO_TARGET_DS_DRIVER,
            havingValue = TargetDataSourceSupport.Driver.ODPS
    )
    public Odps odps(){
        DruidDataSource  targetDataSource = applicationContext.getBean(DS_TARGET, DruidDataSource.class);
        DruidPooledConnection druidPooledConnection = targetDataSource.getConnection();
        ConnectionProxyImpl connectionProxy = (ConnectionProxyImpl)druidPooledConnection.getConnection();
        OdpsConnection castCon = (OdpsConnection)connectionProxy.getConnectionRaw();
        return castCon.getOdps();
    }
}
