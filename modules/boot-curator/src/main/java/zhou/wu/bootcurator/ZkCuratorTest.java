package zhou.wu.bootcurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author zhou.wu
 * @description: ZkCurator测试
 * @date 2023/1/9
 **/
public class ZkCuratorTest {
    public static final String ZK_ADDRESS = "localhost:2181";

    public static final String COMMON_PATH = "/lxc_test";

    public static void main(String[] args) throws Exception {
        CuratorFramework zkClient = CuratorFrameworkFactory.builder().connectString(ZK_ADDRESS)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 5))
                .build();

        zkClient.start();
        try {
            // 创建一个节点，初始内容为空
            // 如果没有设置节点属性，节点创建模式默认为持久化节点，内容默认为空
//            zkClient.create().forPath(COMMON_PATH);

            // 创建一个节点，附带初始化内容
            zkClient.create().forPath(COMMON_PATH+"/001", "content001".getBytes());

//        // 创建一个节点，指定创建模式（临时节点），内容为空
//        zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(COMMON_PATH);
//
//        // 创建一个节点，指定创建模式（临时节点），附带初始化内容
//        zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(COMMON_PATH, "contect".getBytes());
//
//        // 创建一个节点，指定创建模式（临时节点），附带初始化内容，并且自动递归创建父节点
//        zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(COMMON_PATH, "contect".getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zkClient.close();
        }
    }
}
