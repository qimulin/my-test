package zhou.wu.boot.schedule.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import zhou.wu.boot.schedule.quartz.Constants;

/**
 * @author zhou.wu
 * @date 2021/11/11
 **/
public class Job1 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long l = System.currentTimeMillis();
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Object businessKey = jobDataMap.get(Constants.BUSINESS_KEY);
        if(Constants.TEST_CACHE_MAP.containsKey(businessKey)){
            System.out.println("正在运行，不予以新任务运行！");
            return;
        }
        System.out.println(l+" start execute job 1 ");
        System.out.println("添加key="+businessKey+" value="+String.valueOf(l)+"的缓存");
        Constants.TEST_CACHE_MAP.put(businessKey, String.valueOf(l));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(l+" end execute job 1 ");
        Constants.TEST_CACHE_MAP.remove(businessKey);
    }
}
