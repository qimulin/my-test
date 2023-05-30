package zhou.wu.bootcurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
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
 * @description ZkCurator 增删改查 测试
 * @date 2023/1/9
 **/
public class ZkCuratorCurdTest {
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

    @Test
    public void test() throws Exception {

            // 创建一个节点，初始内容为空
            // 如果没有设置节点属性，节点创建模式默认为持久化节点，内容默认为空
//            zkClient.create().forPath(COMMON_PATH);

            // 创建一个节点，附带初始化内容
            zkClient.create().forPath("/0529/1723", "content001".getBytes());

//        // 创建一个节点，指定创建模式（临时节点），附带初始化内容，并且自动递归创建父节点
//        zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(COMMON_PATH, "contect".getBytes());
    }

    @Test
    public void testCreate() throws Exception {
        // 这里创建节点虽然没有指定数据，但是默认当前客户端机器的ip地址作为数据
//        zkClient.create().forPath("/01");

        // 指定数据创建节点
//        zkClient.create().forPath("/02","你好吗".getBytes(StandardCharsets.UTF_8));

        // withMode 指定节点类型，本例测试创建临时节点
//        zkClient.create().withMode(CreateMode.EPHEMERAL).forPath("/tmp","临时数据".getBytes(StandardCharsets.UTF_8));
//        // 由于客户端关闭，临时节点便不在了，则此处即可进行查询。节点会随本会话存在而存在，本会话没被关闭，也能被其他会话查询到本会话创建的临时节点
//        // 若本会话被异常中断，则其他会话获取本临时节点数据也还能查询到。但是服务端重启，也会被检查到，重新删除
//        byte[] bytes = zkClient.getData().forPath("/tmp");
//        System.out.println(new String(bytes));

        // 传参多级节点，当父节点不存在，则会报错，如下：
//        zkClient.create().forPath("/03/001");
        // 可改为添加creatingParentsIfNeeded（如果父节点不存在，则创建父节点），如下：
        zkClient.create().creatingParentsIfNeeded().forPath("/03/001");
    }

    @Test
    public void testQuery() throws Exception {
        // 查询子节点
        List<String> list = zkClient.getChildren().forPath("/03");
        System.out.println(Arrays.toString(list.toArray()));
        // 获取节点信息
        // Stat承载节点状态信息
        Stat stat = new Stat();
        System.out.println(stat);
        // storingStatIn将状态信息放到stat对象中
        zkClient.getData().storingStatIn(stat).forPath("/03");
        System.out.println(stat);
    }

    @Test
    public void testSet() throws Exception {
        // 修改数据
        zkClient.setData().forPath("/03", "content 03".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(zkClient.getData().forPath("/03")));
        // 根据版本进行修改（类似乐观锁）
        // 查询版本
        Stat stat = new Stat();
        zkClient.getData().storingStatIn(stat).forPath("/03");
        int version = stat.getVersion();
        System.out.println(version);
        // withVersion，version不对即会失败
        zkClient.setData().withVersion(version).forPath("/03", "content 03 version".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(zkClient.getData().forPath("/03")));
    }

    @Test
    public void testDelete() throws Exception {
        // 删除单个节点（不允许带有子节点）
//        zkClient.delete().forPath("/03/002");
        // 删除带有子节点的节点deletingChildrenIfNeeded
//        zkClient.delete().deletingChildrenIfNeeded().forPath("/03");
        // 必须成功的删除 guaranteed 必然的 美[ˌɡærənˈtiːd] 内部有重试机制，可以解决因网络抖动原因导致的节点没删除成功
//        zkClient.delete().guaranteed().forPath("/03/002");
        // 带有删除后回调的删除
        BackgroundCallback backgroundCallback = new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("backgroundCallback");
                System.out.println(curatorEvent);
            }
        };
        zkClient.delete().guaranteed().inBackground(backgroundCallback).forPath("/03/002");
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
                         }
                     }
        );
        // 3、开启监听
    }

    @After
    public void closeZkClient(){
        if(null!=zkClient){
            zkClient.close();
        }
    }

}
