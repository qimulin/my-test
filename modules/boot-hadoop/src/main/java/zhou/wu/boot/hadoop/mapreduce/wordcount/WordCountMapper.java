package zhou.wu.boot.hadoop.mapreduce.wordcount;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author zhou.wu
 * @date 2022/6/21
 * KEYIN map阶段输入的key的类型，本例为偏移量，类型为LongWritable
 * VALUEIN map阶段输入value类型，本例为行内容，所以是Text
 * KEYOUT map阶段输出的key类型，本例为单词，所以是Text
 * VALUEOUT map阶段输出的value类型，本例为单词个数：IntWritable
 **/
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

    private Text k = new Text();
    private IntWritable v = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context)	throws IOException, InterruptedException {

        // 1 获取一行
        String line = value.toString();

        // 2 切割
        String[] words = line.split(" ");

        // 3 输出
        for (String word : words) {
            k.set(word);
            context.write(k, v);
        }
    }
}

