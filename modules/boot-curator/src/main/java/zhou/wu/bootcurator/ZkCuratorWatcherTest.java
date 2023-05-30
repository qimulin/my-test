package zhou.wu.bootcurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhou.wu
 * @description ZkCurator Watcher测试
 * @date 2023/1/9
 **/
public class ZkCuratorWatcherTest {
    public static final String ZK_ADDRESS = "localhost:2181";

    public static final String COMMON_PATH = "lxc";

    private CuratorFramework zkClient;

    @Before
    public void startZkClient(){
        zkClient = CuratorFrameworkFactory.builder().connectString(ZK_ADDRESS)
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
    }

    /**
     * Curator引入了Cache来实现Zookeeper服务端事件的监听
     * Zookeeper提供了三种Watcher
     * - NodeCache：只是监听某一特定的节点
     * - PathChildrenCache：监听一个Znode的子节点
     * - TreeCache：可以监控整个树上的所有节点，类似于PathChildrenCache和NodeCache的组合
     * */
    @Test
    public void testWatcherNodeCache() throws Exception {
        // 1、创建NodeCache对象，也可以使用两个参数的构造。 dataIsCompressed：数据是否压缩，压缩的话传输会变快，但是读取的时候需要解压缩
        NodeCache nodeCache = new NodeCache(zkClient, "/03", false);
        // 2、注册监听
        // 获取可用的监听
        nodeCache.getListenable()
            // 添加监听器
            .addListener(
                    new NodeCacheListener() {
                         @Override
                         public void nodeChanged() throws Exception {
                             System.out.println("nodeChanged");
                             // 获取修改后的节点数据，还可以getPath、getStat等
                             ChildData currentData = nodeCache.getCurrentData();
                             if(currentData==null){
                                 System.out.println("currentData is null");
                                 return;
                             }
                             byte[] data = nodeCache.getCurrentData().getData();
                             if(data==null){
                                 System.out.println("data is null");
                                 return;
                             }
                             System.out.println("changed data:"+new String(data));
                         }
                     }
        );
        // 3、开启监听。如果buildInitial设置为true，则开启监听时，加载缓存数据
        nodeCache.start(true);
        // 模拟服务器一直开着
        while(true){

        }
    }

    @Test
    public void testWatcherPathChildrenCache() throws Exception {
        // 1、创建NodeCache对象
        PathChildrenCache cache = new PathChildrenCache(zkClient, "/03", true);
        // 2、绑定监听器
        // 获取可用的监听
        cache.getListenable()
                // 添加监听器
                .addListener(
                        new PathChildrenCacheListener() {
                            /**
                             * 感知不到当前节点的变化的，只能监听子节点的变化情况，对子节点的子节点变化也不感知
                             * */
                            @Override
                            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                                System.out.println("childEvent");
                                System.out.println(pathChildrenCacheEvent);
                                // 若要监听子节点的数据变更
                                PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
                                if(type==PathChildrenCacheEvent.Type.CHILD_UPDATED){
                                    // 获取数据
                                    byte[] data = pathChildrenCacheEvent.getData().getData();
                                    System.out.println("update data:"+new String(data));
                                }
                            }
                        }
                );
        // 3、开启监听
        cache.start();
        // 模拟服务器一直开着
        while(true){

        }
    }

    @Test
    public void testWatcherTreeCache() throws Exception {
        // 1、创建NodeCache对象
        TreeCache cache= new TreeCache(zkClient, "/03");
        // 2、绑定监听器
        // 获取可用的监听
        cache.getListenable()
                // 添加监听器
                .addListener(
                        new TreeCacheListener() {
                            @Override
                            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                                System.out.println("childEvent");
                            }

                        }
                );
        // 3、开启监听
        cache.start();
        // 模拟服务器一直开着
        while(true){

        }
    }

    @After
    public void closeZkClient(){
        if(null!=zkClient){
            zkClient.close();
        }
    }

}
