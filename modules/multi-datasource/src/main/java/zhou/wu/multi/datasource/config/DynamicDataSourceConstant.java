package zhou.wu.multi.datasource.config;

/**
 * 动态数据源相关
 * @author zhou.wu
 * @date 2022/3/2
 **/
public class DynamicDataSourceConstant {

    public static final String PREFIX_SYSTEM_DS = "spring.datasource.system";
    public static final String PREFIX_SYSTEM_DS_DRUID = PREFIX_SYSTEM_DS+".druid";
    public static final String PREFIX_TARGET_DS = "spring.datasource.target";
    public static final String PREFIX_TARGET_DS_DRUID = PREFIX_TARGET_DS+".druid";
    public static final String PREFIX_LGYG_DS = "spring.datasource.lgyg";
    public static final String PREFIX_LGYG_DS_DRUID = PREFIX_LGYG_DS+".druid";

    public static final String PRO_TARGET_DS_DRIVER = PREFIX_TARGET_DS_DRUID +".driver-class-name";

    public static final String DS_SYSTEM = "system";
    public static final String DS_TARGET = "target";
    public static final String DS_LGYG = "lgyg";

}
