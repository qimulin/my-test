package zhou.wu.boot.schedule.quartz;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants
 */
public class Constants {

    /**
     * quartz config
     */
    public static final String ORG_QUARTZ_JOBSTORE_DRIVERDELEGATECLASS = "org.quartz.jobStore.driverDelegateClass";
    public static final String ORG_QUARTZ_SCHEDULER_INSTANCENAME = "org.quartz.scheduler.instanceName";
    public static final String ORG_QUARTZ_SCHEDULER_INSTANCEID = "org.quartz.scheduler.instanceId";
    public static final String ORG_QUARTZ_SCHEDULER_MAKESCHEDULERTHREADDAEMON = "org.quartz.scheduler.makeSchedulerThreadDaemon";
    public static final String ORG_QUARTZ_JOBSTORE_USEPROPERTIES = "org.quartz.jobStore.useProperties";
    public static final String ORG_QUARTZ_THREADPOOL_CLASS = "org.quartz.threadPool.class";
    public static final String ORG_QUARTZ_THREADPOOL_THREADCOUNT = "org.quartz.threadPool.threadCount";
    public static final String ORG_QUARTZ_THREADPOOL_MAKETHREADSDAEMONS = "org.quartz.threadPool.makeThreadsDaemons";
    public static final String ORG_QUARTZ_THREADPOOL_THREADPRIORITY = "org.quartz.threadPool.threadPriority";
    public static final String ORG_QUARTZ_JOBSTORE_CLASS = "org.quartz.jobStore.class";
    public static final String ORG_QUARTZ_JOBSTORE_TABLEPREFIX = "org.quartz.jobStore.tablePrefix";
    public static final String ORG_QUARTZ_JOBSTORE_ISCLUSTERED = "org.quartz.jobStore.isClustered";
    public static final String ORG_QUARTZ_JOBSTORE_MISFIRETHRESHOLD = "org.quartz.jobStore.misfireThreshold";
    public static final String ORG_QUARTZ_JOBSTORE_CLUSTERCHECKININTERVAL = "org.quartz.jobStore.clusterCheckinInterval";
    public static final String ORG_QUARTZ_JOBSTORE_ACQUIRETRIGGERSWITHINLOCK = "org.quartz.jobStore.acquireTriggersWithinLock";
    public static final String ORG_QUARTZ_JOBSTORE_DATASOURCE = "org.quartz.jobStore.dataSource";
    public static final String ORG_QUARTZ_DATASOURCE_MYDS_CONNECTIONPROVIDER_CLASS = "org.quartz.dataSource.myDs.connectionProvider.class";

    /**
     * quartz config default value
     */
    public static final String QUARTZ_TABLE_PREFIX = "QRTZ_";
    public static final String QUARTZ_MISFIRETHRESHOLD = "60000";
    public static final String QUARTZ_CLUSTERCHECKININTERVAL = "5000";
    public static final String QUARTZ_DATASOURCE = "myDs";
    public static final String QUARTZ_THREADCOUNT = "25";
    public static final String QUARTZ_THREADPRIORITY = "5";
    public static final String QUARTZ_INSTANCENAME = "DolphinScheduler";
    public static final String QUARTZ_INSTANCEID = "AUTO";
    public static final String QUARTZ_ACQUIRETRIGGERSWITHINLOCK = "true";

    /**
     * underline  "_"
     */
    public static final String UNDERLINE = "_";
    /**
     * quartz job prifix
     */
    public static final String QUARTZ_JOB_PRIFIX = "job";
    /**
     * quartz job group prifix
     */
    public static final String QUARTZ_JOB_GROUP_PRIFIX = "jobgroup";

    public static final String BUSINESS_KEY = "businessKey";

    public static Map<Object, String> TEST_CACHE_MAP = new HashMap<>();

}
