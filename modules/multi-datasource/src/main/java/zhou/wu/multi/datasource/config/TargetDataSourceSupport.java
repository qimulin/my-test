package zhou.wu.multi.datasource.config;

/**
 * @author lin.xc
 * @date 2021/2/24
 **/
public class TargetDataSourceSupport {

    /** 支持的驱动类型 */
    interface Driver{
        String MYSQL = "com.mysql.cj.jdbc.Driver";
        String ODPS = "com.aliyun.odps.jdbc.OdpsDriver";
    }
}
