package zhou.wu.bootcurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author zhou.wu
 * @description 模拟12306卖票服务
 * @date 2023/6/1
 **/
public class Ticket12306 implements Runnable{

    public static final String ZK_ADDRESS = "localhost:2181";

    public static final String COMMON_PATH = "lxc";

    /** 票数 */
    private int tickets = 10;

    /** 分布式可重入排他锁 */
    private InterProcessMutex lock;

    public Ticket12306() {
        CuratorFramework zkClient = CuratorFrameworkFactory.builder().connectString(ZK_ADDRESS)
                // 命名空间（后续地址都会以此节点为基准）
                .namespace(COMMON_PATH)
                // 会话超时时间，建立连接后，超过多久没通信
                .sessionTimeoutMs(5000)
                // 连接超时时间
                .connectionTimeoutMs(3000)
                // 重试策略（ExponentialBackoffRetry可指定重试间隔，最大重试次数）
                .retryPolicy(new ExponentialBackoffRetry(1000, 5))
                .build();
        zkClient.start();
        lock = new InterProcessMutex(zkClient, "/lock");
    }

    @Override
    public void run() {
        // 当做一直提供服务
        while(true){
            try {
                // 获取锁
                lock.acquire(3, TimeUnit.SECONDS);
                if(tickets>0){
                    System.out.println(Thread.currentThread()+":"+tickets);
                    // 模拟业务处理的久一点
                    Thread.sleep(1000);
                    tickets--;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
