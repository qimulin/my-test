package zhou.wu.mytest.threadpool.write;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 手写一个线程池
 *
 * @author Lin.xc
 * @date 2020/5/14
 */
public class FixedSizeThreadPool {
    /**
     * 如果需要手写一个线程池，我们需要什么？
     * 1、需要一个仓库；
     * 2、需要一个放线程的集合；
     * 3、需要一个码农来干活；
     * 4、需要初始化仓库和线程集合；
     * 5、需要向仓库放任务的方法“不阻塞”返回一个特殊值
     * 6、需要向仓库放任务的方法“阻塞”
     * 7、需要一个关闭线程池的方法
     */
    // 需要一个仓库
    private BlockingQueue<Runnable> blockingQueue;

    // 需要一个放线程的集合
    private List<Thread> workers;

    // 需要干活的
    public static class Worker extends Thread {

        private FixedSizeThreadPool pool;

        public Worker(FixedSizeThreadPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            super.run();
        }
    }
}
