package zhou.wu.boot.hadoop.mapreduce.wordcount;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author zhou.wu
 * @date 2022/6/21
 **/
public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // 1 获取配置信息以及获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 关联本Driver程序的jar
        job.setJarByClass(WordCountDriver.class);

        // 3 关联Mapper和Reducer的jar
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 4 设置Mapper输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5 设置最终输出kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

     /*   System.out.println("------ 设置InputFormat ------");
        // 如果不设置InputFormat，它默认用的是TextInputFormat.class
        // CombineTextInputFormat方便处理很多小文件，组合起来再分配MapTask,不至于开启过多的MapTask
        job.setInputFormatClass(CombineTextInputFormat.class);
        //虚拟存储切片最大值设置4m
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);*/

       /* System.out.println("------ 设置NumReduceTask ------");
        // numReduceTask会影响分区的个数
        job.setNumReduceTasks(2);*/

        // 6 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\TempFile\\202206\\23\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\TempFile\\202206\\23\\output4"));

//        FileInputFormat.setInputPaths(job, new Path(args[0]));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7 提交job 参数verbose决定是否监控和打印job信息
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
