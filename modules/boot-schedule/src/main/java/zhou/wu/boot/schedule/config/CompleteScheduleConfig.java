package zhou.wu.boot.schedule.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author lin.xc
 * @date 2020/10/20
 **/
//@Configuration 暂无该表，暂时注释
@EnableScheduling
public class CompleteScheduleConfig implements SchedulingConfigurer {

    @Mapper
    public interface CronMapper {

        @Select("select cron from cron where cron_id=1")
        String getCron1();

        @Select("select cron from cron where cron_id=2")
        String getCron2();
    }

    @Autowired
    @SuppressWarnings("all")
    CronMapper cronMapper;

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> System.out.println("执行定时任务1: " + LocalDateTime.now().toLocalTime()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron1 = cronMapper.getCron1();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron1)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron1).nextExecutionTime(triggerContext);
                }
        );

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> System.out.println("执行定时任务2: " + LocalDateTime.now().toLocalTime()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron2 = cronMapper.getCron2();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron2)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron2).nextExecutionTime(triggerContext);
                }
        );
    }

}