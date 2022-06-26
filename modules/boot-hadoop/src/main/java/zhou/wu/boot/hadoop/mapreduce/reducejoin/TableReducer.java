package zhou.wu.boot.hadoop.mapreduce.reducejoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TableReducer extends Reducer<Text,TableBean,TableBean, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {

        ArrayList<TableBean> orderBeans = new ArrayList<>();
        TableBean pdBean = new TableBean();

        // 自解：hadoop源码有做过修改，遍历时会把新遍历到的TableBean赋给value，所以已经add到集合的value会被，所以需要另外实例化一个对象接收当前遍历到的TableBean的值
        for (TableBean value : values) {
            //判断数据来自哪个表
            if("order".equals(value.getFlag())){   //订单表
                //创建一个临时TableBean对象接收value
                TableBean tmpOrderBean = new TableBean();
                try {
                    BeanUtils.copyProperties(tmpOrderBean,value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                //将临时TableBean对象添加到集合orderBeans
                orderBeans.add(tmpOrderBean);
            }else {                                    //商品表
                try {
                    BeanUtils.copyProperties(pdBean,value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        //遍历集合orderBeans,替换掉每个orderBean的pid为pname,然后写出
        for (TableBean orderBean : orderBeans) {

            orderBean.setPname(pdBean.getPname());

            //写出修改后的orderBean对象
            context.write(orderBean,NullWritable.get());
        }
    }
}

