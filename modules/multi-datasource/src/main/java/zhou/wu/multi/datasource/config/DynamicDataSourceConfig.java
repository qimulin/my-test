package zhou.wu.multi.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.aliyun.odps.Odps;
import com.aliyun.odps.jdbc.OdpsConnection;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author: lin.xc
 * @create: 2021-2-23
 **/
@Configuration
public class DynamicDataSourceConfig {

    private static final String PREFIX_SYSTEM_DS = "spring.datasource.system.druid";
    private static final String PREFIX_TARGET_DS = "spring.datasource.target.druid";

    /** 该属性值，请与下列方法名一致 */
    public static final String BEAN_NAME_TARGET_DS = "targetDataSource";

    /**
     * 系统库
     * */
    @Bean
    @ConfigurationProperties(PREFIX_SYSTEM_DS)
    public DruidDataSource systemDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 目标库
     * 【注意】若要支持读个目标库，这样写不大好，最好能动态实现加载，但时间有限，有时间再研究下那种比较优雅的写法
     * */
    @Bean
    @ConfigurationProperties(PREFIX_TARGET_DS)
    public DruidDataSource targetDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(
            DataSource systemDataSource,
            DataSource targetDataSource
    ){
        // 构造DynamicDataSource，并注入Spring
        return new DynamicDataSource(systemDataSource, targetDataSource);
    }

    /**
     * 必需有目标数据源的Bean，且为Odps
     * 【说明】：系统中存在需要查询数据源中表和字段的元数据信息，但是Odps数据源并不如Mysql那样访问information_schema方便，详见阿里云
     * TODO：才疏学浅，目前只能想到这样比较简单，有更优雅的写法，欢迎来改
     * */
    @Bean
    @SneakyThrows
    @ConditionalOnBean(name=BEAN_NAME_TARGET_DS)
    @ConditionalOnProperty(
            name = PREFIX_TARGET_DS+".driver-class-name",
            havingValue = "com.aliyun.odps.jdbc.OdpsDriver"
    )
    public Odps odps(
            DruidDataSource targetDataSource
    ){
        DruidPooledConnection druidPooledConnection = targetDataSource.getConnection();
        ConnectionProxyImpl connectionProxy = (ConnectionProxyImpl)druidPooledConnection.getConnection();
        OdpsConnection castCon = (OdpsConnection)connectionProxy.getConnectionRaw();
        return castCon.getOdps();
    }
}
