package zhou.wu.boot.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @author zhou.wu
 * @description: HDFS客户端
 * @date 2022/6/20
 * 常用套路：
 * 1.获取客户端对象
 * 2.执行操作命令
 * 3.关闭资源
 **/
public class HdfsClient {

    private FileSystem fs;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "2");
        // FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration);
        fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "develop");
    }

    @After
    public void close() throws IOException {
        // 关闭资源
        fs.close();
    }

    @Test
    public void testMkdirs() throws IOException, URISyntaxException, InterruptedException {
        // 创建目录
        fs.mkdirs(new Path("/xiyou/huaguoshan1/"));
    }

    /**
     * 参数优先级排序：
     * （1）客户端代码中设置的值 >（2）ClassPath下的用户自定义配置文件 >
     * （3）然后是服务器的自定义配置（xxx-site.xml） >（4）服务器的默认配置（xxx-default.xml）
     */
    @Test
    public void testPut() throws IOException {
        // 上传
        fs.copyFromLocalFile(new Path("D:\\TempFile\\sunwukong.txt"), new Path("/xiyou/huaguoshan"));
    }

    @Test
    public void testGet() throws IOException {
        // 执行下载操作
        // boolean delSrc 指是否将原文件删除
        // Path src 指要下载的文件路径
        // Path dst 指将文件下载到的路径X`
        // boolean useRawLocalFileSystem 是否开启文件校验
        fs.copyToLocalFile(false, new Path("/xiyou/huaguoshan/sunwukong.txt"),
                new Path("d:/TempFile/sunwukong2.txt"), true);
    }

    @Test
    public void testRm() throws IOException {
        // 执行删除
        fs.delete(new Path("/jinguo"), true);
    }

    @Test
    public void testMv() throws IOException {
        // 移动或修改文件名称，还可用于目录的更名
        fs.rename(new Path("/xiyou/huaguoshan/sunwukong.txt"), new Path("/xiyou/huaguoshan/meihouwang.txt"));

    }

    @Test
    public void testListFiles() throws IOException {

        // 获取文件详情
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        // 遍历
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println("========" + fileStatus.getPath() + "=========");
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());
            // 获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println(Arrays.toString(blockLocations));
        }
    }

    @Test
    public void testListStatus() throws IOException {
        // 判断是文件还是文件夹
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : listStatus) {
            // 如果是文件
            if (fileStatus.isFile()) {
                System.out.println("f:"+fileStatus.getPath().getName());
            }else {
                System.out.println("d:"+fileStatus.getPath().getName());
            }
        }
    }


}