package zhou.wu.boot.hadoop.mapreduce.writable.comparable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import zhou.wu.boot.hadoop.mapreduce.writable.FlowBean;

import java.io.IOException;

/**
 * @author zhou.wu
 * @date 2022/6/21
 **/
public class ComparableFlowReducer extends Reducer<ComparableFlowBean, Text, Text, ComparableFlowBean> {

    @Override
    protected void reduce(ComparableFlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // 遍历values集合[本例中为流量情况，并按总流量降序],循环写出,避免总流量相同的情况
        for (Text value : values) {
            // 调换KV位置,反向写出
            context.write(value,key);
        }
    }
}
