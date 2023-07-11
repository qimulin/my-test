package zhou.wu.boot_xxl_job.sift_log;

/**
 * @author zhou.wu
 * @description 常量类
 * @date 2023/7/11
 **/
public class SiftConstants {

    /**
     * 业务日志前缀
     */
    public static final String SIFT_LOGGER_INFO_PREFIX = "BIZ";
    /**
     * 筛选日志名
     */
    public static final String SIFT_LOG_LOGGER_NAME = "SiftLogLogger";
    /**
     * 筛选日志名称格式
     */
    public static final String SIFT_LOG_LOGGER_NAME_FORMAT = SIFT_LOG_LOGGER_NAME + "-%s";
    /**
     * 筛选日志线程名称
     */
    public static final String SIFT_LOGGER_THREAD_NAME = "SiftLogInfo";

    /**
     * 筛选日志线程名称格式
     */
    public static final String SIFT_LOGGER_THREAD_NAME_FORMAT = SIFT_LOGGER_THREAD_NAME + "-%s";

    public static final String GET_OUTPUT_LOG_SERVICE = "-getOutputLogService";
}
