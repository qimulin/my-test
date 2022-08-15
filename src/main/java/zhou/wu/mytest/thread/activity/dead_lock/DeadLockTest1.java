package zhou.wu.mytest.thread.activity.dead_lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zhou.wu
 * @description: 死锁测试
 * @date 2022/8/15
 **/
@Slf4j
public class DeadLockTest1 {
    public static void main(String[] args) {
        Object A = new Object();
        Object B = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                log.debug("lock A");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    log.debug("lock B");
                    log.debug("操作...");
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (B) {
                log.debug("lock B");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A) {
                    log.debug("lock A");
                    log.debug("操作...");
                }
            }
        }, "t2");
        t1.start();
        t2.start();
    }

}
