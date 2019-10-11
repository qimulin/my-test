package zhou.wu.mytest.scheduler.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 使用 Quartz 进行任务调度-每隔2秒钟打印一次HelloQuartz
 * @author Lin.xc
 * @date 2019/10/8
 *  * 使用者只需要创建一个 Job 的继承类，实现 execute 方法。JobDetail 负责封装 Job 以及 Job 的属性，并将其提供给 Scheduler 作为参数。
 *  * 每次 Scheduler 执行任务时，首先会创建一个 Job 的实例，然后再调用 execute 方法执行。
 *  * Quartz 没有为 Job 设计带参数的构造函数，因此需要通过额外的 JobDataMap 来存储 Job 的属性。
 *  * JobDataMap 可以存储任意数量的 Key，Value 对
 */
public class QuartzTest implements Job {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(QuartzTest.class).withIdentity("myJob").build();
        //jobDetailDataMap存储数据
        jobDetail.getJobDataMap().put("myDescription", "my job description");
        jobDetail.getJobDataMap().put("myValue", 1998);
        ArrayList<String> list = new ArrayList<String>();
        list.add("item1");
        jobDetail.getJobDataMap().put("myArray", list);
        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        //创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,trigger);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取jobDetailDataMap中的数据
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        int myValue = jobDataMap.getIntValue("myValue");
        //打印当前的执行时间 例如 2017-11-23 00:00:00
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("现在的时间是："+ sf.format(date));
        //具体的业务逻辑
        System.out.println("Hello Quartz "+myValue);
    }

}