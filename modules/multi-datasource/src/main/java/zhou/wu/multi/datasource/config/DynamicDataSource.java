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

    /** 数据源控制 */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /** 数据源集合（父类的targetDataSources不可访问，所以记录下来，方便操作数据源选择） */
    public static Map<Object, Object> dataSourceMap = new HashMap<>(3);

    /**
     * 构造方法，需要传入默认的数据源
     * */
    public DynamicDataSource(DataSource defaultDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
    }

    /** 初始化设置 */
    public void init(){
        super.setTargetDataSources(dataSourceMap);
        super.afterPropertiesSet();
    }

    /** 添加数据源 */
    public void addDatasource(String dataSourceKey, DataSource dataSource){
        this.dataSourceMap.put(dataSourceKey, dataSource);
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