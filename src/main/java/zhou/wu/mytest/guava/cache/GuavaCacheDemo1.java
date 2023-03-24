package zhou.wu.mytest.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhou.wu
 * @description CacheBuilder
 * @date 2023/3/16
 **/
@Slf4j
public class GuavaCacheDemo1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testAutoCloseable();
        Cache<String, DemoClient> clientCache = CacheBuilder.newBuilder()
                // 缓存项在给定时间内没有被读/写访问，则回收
//                .expireAfterWrite(7, TimeUnit.SECONDS)
                .expireAfterAccess(7, TimeUnit.SECONDS)
                .removalListener((RemovalListener<String, DemoClient>) notification -> {
                    try (DemoClient closedClient = notification.getValue()) {
                        log.info("DemoClient: {} is removed from cache due to expire", notification.getKey());
                    }
                }).maximumSize(100)
                .build();

        String key1 = "zhangsan", key2 = "lisi";;
        // 获取一个，无的话会新建
        getDemoClientFromClientCache(clientCache, key1);
        log.info("clientCache size: {}", clientCache.size());
        // ----- 1 ------
//        // 大于过期时间，便可过期，只不过并没有监控线程监控它马上过期就会被移除
//        TimeUnit.SECONDS.sleep(10);
//        // 按测试结果看，getIfPresent并不会触发移除通知
//        log.info("getIfPresent");
//        clientCache.getIfPresent(demoClient.getId());
//        // put方法会触发queue的poll操作，如果取出来元素的话，就执行OnRemoval操作
//        log.info("put");
//        DemoClient lisi = new DemoClient(key2);
//        clientCache.put(lisi.getId(), lisi);
//        log.info("get");
//        clientCache.get(demoClient.getId());
//        clientCache.get(demoClient.getId(), new Callable<DemoClient>() {
//            @Override
//            public DemoClient call() throws Exception {
//                return demoClient;
//            }
//        });
//        log.info("clientCache size: {}", clientCache.size());

        // ----- 2 ------
//        DemoClient lisi = new DemoClient(key2);
//        clientCache.put(lisi.getId(), lisi);
//        // 小于过期时间，再去读写，配合expireAfterAccess方法
//        // 保持获取，刷新访问周期
//        Thread t1 = new Thread(() -> {
//            int i=3;
//            while(i>0){
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("keep client, i:{}, id: {}", i, key1);
//                clientCache.getIfPresent(key1);
//                log.info("clientCache size: {}", clientCache.size());
//                i--;
//            }
//        });
//        t1.start();
//
//        // t1线程应该循环结束了
//        TimeUnit.SECONDS.sleep(30);
//        // 2-1 访问过期后再去读（触发），则已经读不到
//        log.info("getIfPresent 1");
//        DemoClient ifPresent = clientCache.getIfPresent(key1);
//        System.out.println(ifPresent);
//        // 2-2 访问过期后再去读，则已经读不到
//        log.info("clientCache size: {}", clientCache.size());
//        log.info("get from cache");
//        getDemoClientFromClientCache(clientCache, key1);

        // ----- 3 ----- 没过期前去get是否能维持，不触发OnRemoval操作
        TimeUnit.SECONDS.sleep(5);
        log.info("3-1 get from cache client");
        System.out.println(getDemoClientFromClientCache(clientCache, key1));
        TimeUnit.SECONDS.sleep(5);
        log.info("3-2 get from cache client");
        System.out.println(getDemoClientFromClientCache(clientCache, key1));
        // 等待更长，过期
        TimeUnit.SECONDS.sleep(10);
        // 原key对应缓存失效后，get其他key也会触发原key缓存的移除
        log.info("3-3 get from cache client");
        System.out.println(getDemoClientFromClientCache(clientCache, key1));

        TimeUnit.SECONDS.sleep(30);
    }

    /**
     * 获取一个DemoClient
     * */
    private static DemoClient getDemoClientFromClientCache(Cache<String, DemoClient> clientCache, String key) throws ExecutionException {
        return clientCache.get(key, new Callable<DemoClient>() {
            @Override
            public DemoClient call() throws Exception {
                return new DemoClient(key);
            }
        });
    }

    /**
     * 测试自动关闭
     * */
    private static void testAutoCloseable(){
        try (DemoClient demoClient = new DemoClient("lisi");){
            log.info("test something id:{}", demoClient.getId());
        }
    }


}

/**
 * 模拟客户端
 * */
@Slf4j
class DemoClient implements AutoCloseable {

    private String id;

    /**
     * 构造方法
     * */
    public DemoClient(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * 资源关闭方法
     * */
    @Override
    public void close() {
       log.info("do close demo client: {}", this.id);
    }
}
