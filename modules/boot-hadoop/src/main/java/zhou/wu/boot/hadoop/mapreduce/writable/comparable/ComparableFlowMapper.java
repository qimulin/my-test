package zhou.wu.boot.hadoop.mapreduce.writable.comparable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import zhou.wu.boot.hadoop.mapreduce.writable.FlowBean;

import java.io.IOException;

/**
 * @author zhou.wu
 * @date 2022/6/21
 **/
public class ComparableFlowMapper extends Mapper<LongWritable, Text, ComparableFlowBean, Text> {

    private ComparableFlowBean outK = new ComparableFlowBean();
    private Text outV = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //1 获取一行数据
        String line = value.toString();

        //2 按照"\t",切割数据
        String[] split = line.split("\t");

        //3 封装outK outV
        outK.setUpFlow(Long.parseLong(split[1]));
        outK.setDownFlow(Long.parseLong(split[2]));
        outK.setSumFlow();
        outV.set(split[0]);

        //4 写出outK outV
        context.write(outK,outV);

    }
}