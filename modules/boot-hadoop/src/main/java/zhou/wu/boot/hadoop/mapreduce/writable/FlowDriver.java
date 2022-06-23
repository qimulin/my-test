package zhou.wu.boot.hadoop.mapreduce.writable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

/**
 * @author zhou.wu
 * @date 2022/6/21
 **/
public class FlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        //1 获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2 关联本Driver类
        job.setJarByClass(FlowDriver.class);

        //3 关联Mapper和Reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        //4 设置Map端输出KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //5 设置程序最终输出的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        System.out.println("------ 设置NumReduceTask ------");
        // numReduceTask会影响分区的个数
        /**【注意】
         * 不能小于设置的分区数Partitioner能生成的分区数n，不然实际生成的n个分区，会需要n个reduceTask；
         * 但是1是例外
         * {@link org.apache.hadoop.mapred.MapTask.NewOutputCollector}实例化的时候会在partitions<=1的时候自己new一个partitioner，且getPartition方法返回0
         * this.partitioner = new Partitioner<K, V>() {
         * 	public int getPartition(K key, V value, int numPartitions) {
         * 		return NewOutputCollector.this.partitions - 1;
         *        }
         * };
         */
        job.setNumReduceTasks(1);
        // 指定分区类
        job.setPartitionerClass(ProvincePartitioner.class);

        //6 设置程序的输入输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\TempFile\\202206\\23\\input_phone_num"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\TempFile\\202206\\23\\output_phone_num1"));

        //7 提交Job
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
