package zhou.wu.mytest.stream.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 聚合操作
 * 流的聚合，聚合是指将流汇聚为一个值，以便在程序中使用。聚合方法都是终止操作。
 * @author Lin.xc
 * @date 2019/10/22
 */
public class StreamAggregation {

    /**
     * max方法和min方法
     * 在前面的代码例子中使用的count方法和sum方法都属于流从聚合方法。
     * 还有两个聚合方法是max方法和min方法，分别返回流中最大值和最小值。
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void maxAndMin(){
        List<Integer> hearList = new ArrayList();
        hearList.add(15);
        hearList.add(32);
        hearList.add(5);
        hearList.add(232);
        hearList.add(56);
        hearList.add(29);
        hearList.add(94);
        Integer maxItem = hearList.stream().max(Integer::compareTo).get();
        Integer minItem = hearList.stream().min(Integer::compareTo).get();
        System.out.println("max:"+maxItem+"，min:"+minItem);
    }

    /**
     * findFirst方法
     * findAny方法
     * anyMatch方法
     * allMatch方法和noneMatch方法
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void manyFind(){
        List<Integer> hearList = new ArrayList();
        hearList.add(15);
        hearList.add(32);
        hearList.add(5);
        hearList.add(232);
        hearList.add(56);
        hearList.add(29);
        hearList.add(104);
        // findFirst方法返回非空集合中的第一个值，它通常与filter方法结合起来使用
        Integer first = hearList.stream().filter(i->i>100).findFirst().get();
        // findAny方法可以在集合中只要找到任何一个所匹配的元素，就返回，
        // 此方法在对流并行执行时十分有效（任何片段中发现第一个匹配元素都会结束计算，串行流中和findFirst返回一样)。
        // parallelStream提供了流的并行处理，它是Stream的另一重要特性，其底层使用Fork/Join框架实现。简单理解就是多线程异步任务的一种实现。
        Integer anyItem = hearList.parallelStream().filter(i->i>100).findAny().get();
        // anyMatch方法可以判定集合中是否还有匹配的元素。返回结果是一个boolean类型值。
        boolean isHas = hearList.parallelStream().anyMatch(i->i>100);
        // allMatch方法和noneMatch方法，分别在所有元素匹配和没有元素匹配时返回true
        boolean allHas = hearList.parallelStream().allMatch(i->i>100);
        boolean noHas = hearList.parallelStream().noneMatch(i->i>100);
    }

    /**
     * reduce方法
     * reduce方法是将流中的元素进行进一步计算的方法。
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void reduce(){
        List<Integer> hearList = new ArrayList();
        hearList.add(15);
        hearList.add(32);
        hearList.add(5);
        hearList.add(232);
        hearList.add(56);
        hearList.add(29);
        hearList.add(104);
        // 求和
        Integer sum = hearList.stream().reduce((x,y)->x+y).get();
        System.out.println("sum:"+sum);
        // 简化一下，求和
        sum = hearList.stream().reduce(Integer::sum).get();
        System.out.println("sum:"+sum);
        // 含有初始标识的，求和
        sum = hearList.stream().reduce(0,(x,y)->x+y);
        System.out.println("sum:"+sum);
        // 对元素的长度进行求和( (total,y)->total+y.toString().length()，类似于一个累加器，会被重复调用)
        sum = hearList.stream().reduce(0,(total,y)->total+y.toString().length(),(total1,total2)->total1+total2);
        System.out.println("sum:"+sum);
        // 简化一下，对元素长度进行求和。
        sum = hearList.stream().map(Objects::toString).mapToInt(String::length).sum();
        System.out.println("sum:"+sum);
    }

}
