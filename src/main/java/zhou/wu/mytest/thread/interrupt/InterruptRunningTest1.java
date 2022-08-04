package zhou.wu.mytest.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhou.wu
 * @description: Sleep方法测试
 * @date 2022/7/30
 **/
@Slf4j
public class InterruptRunningTest1 {
    public static void main(String[] args) throws Exception {
        Thread t2 = new Thread(()->{
            // 用死循环，而不是用sleep，因为中断sleep后TIMED_WAITING状态下的线程和中断RUNNING状态的线程效果不一样
            while(true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                // 光是对线程执行interrupt方法，只是通知线程有其他线程要来打断你，但本身线程的执行并不会因为interrupt方法执行而被打断
                // 因此，可以在线程内部由isInterrupted()得到打断标记进行判断，因为如果是RUNNING状态下被打断的话，打断标记会是true，这时可以在线程内部进行判断和程序终止
                if(interrupted) {
                    System.out.println(" 打断状态: {}" + interrupted);//打断状态: {}true
                    break;
                }
            }
        }, "t2");
        t2.start();
        Thread.sleep(500);
        t2.interrupt();
    }
}
