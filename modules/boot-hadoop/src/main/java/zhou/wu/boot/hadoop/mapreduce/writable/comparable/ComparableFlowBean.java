package zhou.wu.boot.hadoop.mapreduce.writable.comparable;

import org.apache.hadoop.io.WritableComparable;
import zhou.wu.boot.hadoop.mapreduce.writable.FlowBean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 可支持排序的Bean
 * @author zhou.wu
 * @date 2022/6/21
 **/
public class ComparableFlowBean implements WritableComparable<ComparableFlowBean> {

    private long upFlow; //上行流量
    private long downFlow; //下行流量
    private long sumFlow; //总流量

    //2 提供无参构造
    public ComparableFlowBean() {
    }

    //3 提供三个参数的getter和setter方法
    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    // 累加求和
    public void setSumFlow() {
        this.sumFlow = this.upFlow + this.downFlow;
    }

    //4 实现序列化和反序列化方法,注意顺序一定要保持一致
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upFlow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();
    }

    // 5 重写ToString，会影响展示
    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

    // 需实现该比较方法
    @Override
    public int compareTo(ComparableFlowBean o) {
        //按照总流量比较,倒序排列
        if(this.sumFlow > o.getSumFlow()){
            return -1;
        }else if(this.sumFlow < o.getSumFlow()){
            return 1;
        }else {
            return 0;
        }
    }
}
