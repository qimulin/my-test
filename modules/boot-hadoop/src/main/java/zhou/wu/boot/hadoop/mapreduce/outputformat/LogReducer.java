package zhou.wu.boot.hadoop.mapreduce.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author zhou.wu
 * @description: 跟Mapper类似，原样文本输出
 * @date 2022/6/24
 **/
public class LogReducer extends Reducer<Text, NullWritable,Text, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        // 防止有相同的数据,迭代写出：value的个数代表出现相同文本的个数
        for (NullWritable value : values) {
            context.write(key,NullWritable.get());
        }
    }
}
