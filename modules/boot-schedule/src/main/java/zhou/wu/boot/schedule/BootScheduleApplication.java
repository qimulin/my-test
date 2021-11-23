package zhou.wu.boot.schedule;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import zhou.wu.boot.schedule.config.DataSourceConfig;
import zhou.wu.boot.schedule.quartz.Constants;
import zhou.wu.boot.schedule.quartz.QuartzExecutors;
import zhou.wu.boot.schedule.quartz.job.Job1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Import(DataSourceConfig.class)
public class BootScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootScheduleApplication.class, args);
        try {
            QuartzExecutors.getInstance().start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

//        Date startDate = getDateBySdf("2021-11-12 00:00:00");
//        Date endDate = getDateBySdf("2021-11-13 00:00:00");
//        Map<String, Object> jobDataMap = new HashMap<>();
//        jobDataMap.put(Constants.BUSINESS_KEY, 1);
//        QuartzExecutors.getInstance()
//                .addJob(Job1.class, "job-1", "test-group",
//                        startDate, endDate, "0/5 * * * * ? ", jobDataMap);
//        QuartzExecutors.getInstance().deleteJob("job-1", "test-group");
    }


    public static Date getDateBySdf(String dateStr){
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse( dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
