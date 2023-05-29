package zhou.wu.bootcurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author zhou.wu
 * @description ZkCurator测试
 * @date 2023/1/9
 **/
public class ZkCuratorTest {
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

    @After
    public void closeZkClient(){
        if(null!=zkClient){
            zkClient.close();
        }
    }

}
