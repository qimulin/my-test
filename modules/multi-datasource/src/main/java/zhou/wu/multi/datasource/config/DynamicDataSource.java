package zhou.wu.multi.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源
 * Spring提供了AbstractRoutingDataSource 根据用户定义的规则选择当前的数据源
 * @author lin.xc
 * @date 2021/2/20
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final String KEY_SYSTEM = "system";
    public static final String KEY_TARGET = "target";

    /** 数据源集合（父类的targetDataSources不可访问，所以记录下来，方便操作数据源选择） */
    public static Map<Object, Object> dataSourceMap = new HashMap<>(2);

    /** 数据源控制 */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public DynamicDataSource(
            DataSource systemDs,
            DataSource targetDs
    ) {
        super.setDefaultTargetDataSource(systemDs);
        dataSourceMap.put(KEY_SYSTEM, systemDs);
        dataSourceMap.put(KEY_TARGET, targetDs);
        super.setTargetDataSources(dataSourceMap);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public static boolean containsDataSource(String dsName) {
        return dataSourceMap.containsKey(dsName);
    }

    public static void setDataSource(String dsName) {
        contextHolder.set(dsName);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }

}