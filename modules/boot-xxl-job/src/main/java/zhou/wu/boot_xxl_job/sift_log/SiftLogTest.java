package zhou.wu.boot_xxl_job.sift_log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zhou.wu
 * @description 测试通过SiftingAppender筛选日志内容到不同位置不同文件存储
 * @date 2023/7/11
 **/
public class SiftLogTest {

    protected final Logger logger = LoggerFactory.getLogger(String.format(SiftConstants.SIFT_LOG_LOGGER_NAME_FORMAT, getClass()));

    public void testOutLog(){
        logger.info("out info");
        logger.warn("out warn");
        logger.error("out error");
    }

    public static void main(String[] args) throws InterruptedException {
        int key=3;
        // 构建线程名称，前两个id，直接写死1和2了，只动态设置第3个id
        String threadName = String.format(SiftConstants.SIFT_LOGGER_THREAD_NAME_FORMAT, "allBizId=" + SiftConstants.SIFT_LOGGER_INFO_PREFIX + "-1-2-" + key);
        System.out.println("threadName="+threadName);
        // 用线程测试
        new Thread(() -> {
            SiftLogTest siftLogTest = new SiftLogTest();
            siftLogTest.testOutLog();
        }, threadName).start();

        // 不隔开会报错
//        TimeUnit.MILLISECONDS.sleep(5);

        // 通过level进入过滤的
        String threadName2 = "ByLevel-key="+key;
        System.out.println("threadName2="+threadName2);
        // 用线程测试
        new Thread(() -> {
            SiftLogTest siftLogTest = new SiftLogTest();
            siftLogTest.testOutLog();
        }, threadName2).start();

    }
}
