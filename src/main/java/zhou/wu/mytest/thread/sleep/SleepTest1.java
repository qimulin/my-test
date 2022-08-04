package zhou.wu.mytest.thread.sleep;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zhou.wu
 * @description: Sleep方法测试
 * @date 2022/7/30
 **/
@Slf4j
public class SleepTest1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.info("enter sleep");
                try {
                    // 内部其实也是调用Thread.sleep的方法，只是可读性更好
                    TimeUnit.SECONDS.sleep(3);
                    log.info("continue execute……current state:{}", getState());
                } catch (InterruptedException e) {
                    // 中断之后，是Runnable状态，自解：好像和sleep结束后的样子不一样，sleep结束后还可以继续运行后续代码的，所以被中断后是不能继续了？
                    log.info("wake up!current state:{}", getState());
                    e.printStackTrace();
                    // 自解：我觉得此时只能算打算睡眠，不能算唤醒，因为它不会继续做后面的事，但可以重新执行run方法
//                    log.info("rerun");
//                    run();
                }
            }
        };

        t1.start();
        Thread.sleep(1000);

        log.info("interrupt t1");
        t1.interrupt();
        // sleep、wait、join 方法都会让线程进入阻塞状态，打断线程会清空打断状态
        log.info("打断标记：{}", t1.isInterrupted());

        Thread.sleep(5000);
    }
}
