package zhou.wu.boot.hadoop.mapreduce.writable.comparable;

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
public class ComparableFlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        //1 获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2 关联本Driver类
        job.setJarByClass(ComparableFlowDriver.class);

        //3 关联Mapper和Reducer
        job.setMapperClass(ComparableFlowMapper.class);
        job.setReducerClass(ComparableFlowReducer.class);

        //4 设置Map端输出KV类型
        job.setMapOutputKeyClass(ComparableFlowBean.class);
        job.setMapOutputValueClass(Text.class);

        //5 设置程序最终输出的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(ComparableFlowBean.class);

        System.out.println("------ 设置NumReduceTask ------");
        // numReduceTask会影响分区的个数，最好和定义的分区数一致
        job.setNumReduceTasks(5);
        // 指定分区类
        job.setPartitionerClass(ProvincePartitioner2.class);

        //6 设置程序的输入输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\TempFile\\202206\\23\\output_phone_num_result"));  // 【注意】：这里是已经统计完的收集流量信息结果
        FileOutputFormat.setOutputPath(job, new Path("D:\\TempFile\\202206\\23\\output_phone_num_c2"));

        //7 提交Job
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
