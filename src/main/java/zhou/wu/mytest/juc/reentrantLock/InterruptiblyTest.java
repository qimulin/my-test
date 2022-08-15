package zhou.wu.mytest.juc.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhou.wu
 * @description: 可打断测试
 * @date 2022/8/15
 **/
@Slf4j
public class InterruptiblyTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            try {
                // 要可打断的话，能不能用lock方法，得用lockInterruptibly()方法
                // 如果没有竞争，那么此方法就会获取lock对象锁
                // 如果有竞争，进入阻塞队列，可以被其他线程用interrupt方法打断
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("等锁的过程中被打断，没有获得锁！");
                // 在这里说明没有获得锁，就不需要往下解锁，直接返回
                return;
            }
//            lock.lock();    // 若用lock是不可被interrupt方法打断的，会一直等锁解开
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        // 主线程加锁加锁，t1线程就会进入阻塞队列
        lock.lock();
        log.debug("获得了锁");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(3);
            t1.interrupt();
            log.debug("执行打断");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
