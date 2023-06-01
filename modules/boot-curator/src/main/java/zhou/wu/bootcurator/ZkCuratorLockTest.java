package zhou.wu.bootcurator;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Zookeeper分布式锁原理：
 * - 核心思想：当客户端要获取锁，则创建节点，使用完锁，则删除该节点
 * 1、 客户端请求获取锁时，在lock（不一定叫lock，可自己取名）节点下创建【临时顺序节点】。
 *      i、临时：若用持久的，当占用锁的客户端宕机，那就不能（删除节点）释放锁了，临时节点会在会话结束的时候自动被删除；
 *      ii、顺序：顺序自动编号的。
 * 2、然后获取lock下面的所有子节点，客户端获取到所有的子节点后，如果发现自己创建的子节点序号最小，那么就认为该客户端获取到了锁，使用完锁后，将该节点删除。
 * 3、如果发现自己创建的节点并非lock所有子节点中最小的，说明自己还没有获取到锁。此时客户端需要找到比自己小的那个节点，同时对其注册事件监听器，监听删除事件。
 *      例如：/lock/2找lock/1监听删除事件；/lock/3找/lock/2监听删除事件
 * 4、如果发现比自己小的那个节点被删除，则客户端的Watcher会收到相应通知，此时再次判断自己创建的节点是否是lock子节点中序号最小的，如果是则获取到了锁。
 *      如果不是则重复以上步骤继续获取比自己小的一个节点并注册监听监听删除事件。
 *
 * Curator实现分布式锁API
 * 在Curator中有五种锁方案:
 * - InterProcessSemaphoreMutex:分布式排它锁(非可重入锁)
 * - InterProcessMutex:分布式可重入排它锁
 * - InterProcessReadWriteLock: 分布式读写锁
 * - InterProcessMultiLock: 将多个锁作为单个实体管理的容器
 * - InterProcessSemaphoreV2: 共享信号量
 *
 * @author zhou.wu
 * @description ZkCurator 锁测试
 * @date 2023/1/9
 **/
public class ZkCuratorLockTest {

    /**
     * 静态代码块调整日志等级
     * */
    static {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<Logger> loggerList = loggerContext.getLoggerList();
        loggerList.forEach(logger -> {
            logger.setLevel(Level.ERROR);
        });
    }

    /**
     * 模拟12306卖票
     * */
    public static void main(String[] args) {
        Ticket12306 ticket12306 = new Ticket12306();
        // 创建客户端
        Thread t1 = new Thread(ticket12306, "携程");
        Thread t2 = new Thread(ticket12306, "飞猪");

        t1.start();
        t2.start();
    }

}
