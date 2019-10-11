package zhou.wu.mytest.scheduler;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer
 * @author Lin.xc
 * @date 2019/9/30
 */
public class TimerTest extends TimerTask {

    private String jobName;

    public TimerTest(String jobName) {
        super();
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println("execute " + jobName);
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        long delay1 = 1L * 1000;
        long period1 = 1000L;
        // 从现在开始 1 秒钟之后，每隔 1 秒钟执行一次 job1
        timer.schedule(new TimerTest("job1"), delay1, period1);
        long delay2 = 2L * 1000;
        long period2 = 2000L;
        // 从现在开始 2 秒钟之后，每隔 2 秒钟执行一次 job2
        timer.schedule(new TimerTest("job2"), delay2, period2);
    }
}
