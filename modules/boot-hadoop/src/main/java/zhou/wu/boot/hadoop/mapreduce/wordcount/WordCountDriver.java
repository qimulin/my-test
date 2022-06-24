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
import zhou.wu.boot.hadoop.mapreduce.wordcount.combiner.WordCountCombiner;

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

       /* System.out.println("------ 设置自定义Combiner ------");
        // 注意：如果没有reduce阶段（可以通过设置job.setNumReduceTasks(0)），那么也不会产生shuffle阶段，那么combiner的设置也就无效了，直接mapper阶段就出去了
        job.setCombinerClass(WordCountCombiner.class);
        // 注意：如果逻辑和Reducer相同，也可以直接用Reducer的逻辑进行聚合，表达例如下：
        job.setCombinerClass(WordCountReducer.class);*/

        // 6 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\TempFile\\202206\\23\\input_word_count"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\TempFile\\202206\\23\\output8"));

//        FileInputFormat.setInputPaths(job, new Path(args[0]));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7 提交job 参数verbose决定是否监控和打印job信息
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
