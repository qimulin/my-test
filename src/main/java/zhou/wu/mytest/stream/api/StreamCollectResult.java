package zhou.wu.mytest.stream.api;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 收集结果
 * @author Lin.xc
 * @date 2019/10/22
 */
public class StreamCollectResult {

    /**
     * 收集到集合
     * 当处理完流之后，通常是想查看一下结果，而不是将他们聚合为一个值。Collectorts类为我们提供了常用的收集类的各个工厂方法。
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void collectToCollection(){
        List<Integer> hereList = new ArrayList();
        hereList.add(15);
        hereList.add(32);
        hereList.add(5);
        hereList.add(232);
        hereList.add(56);
        hereList.add(29);
        hereList.add(104);
        // 例如前面的例子用的要将一个流收集到一个List中，只需要这样写就可以。
        List<Integer> thereList = hereList.stream().collect(Collectors.toList());
        // 收集到Set中可以这样用
        Set<Integer> thereSet = hereList.stream().collect(Collectors.toSet());
        // 收集到Set时，控制Set的类型，可以这样
        TreeSet<Integer> treeSet = hereList.stream().collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * 拼接
     * 将字流中的字符串连接并收集起来。
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void join(){
        List<String> stringList = new ArrayList();
        stringList.add("5");
        stringList.add("32");
        stringList.add("5");
        stringList.add("232");
        stringList.add("56");
        stringList.add("29");
        stringList.add("104");
        // 将字流中的字符串连接并收集起来。
        String resultString1 = stringList.stream().collect(Collectors.joining());
        // 在将流中的字符串连接并收集起来时，想在元素中介添加分隔符，传递个joining方法即可。
        String resultString2 = stringList.stream().collect(Collectors.joining(","));
        // 当流中的元素不是字符串时，需要先将流转成字符串流再进行拼接。
        List<Integer> hereList = new ArrayList();
        hereList.add(15);
        hereList.add(32);
        hereList.add(5);
        hereList.add(232);
        hereList.add(56);
        hereList.add(29);
        hereList.add(104);
        String hereResultString = hereList.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(hereResultString);
    }

    /**
     * 收集聚合
     * 分别收集流的总和、平均值、最大值或者最小值。
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void collectAggregate(){
        List<Integer> hereList = new ArrayList();
        hereList.add(15);
        hereList.add(32);
        hereList.add(5);
        hereList.add(232);
        hereList.add(56);
        hereList.add(29);
        hereList.add(104);
        // 总和、平均值，最大值，最小值
        int sum = hereList.stream().collect(Collectors.summingInt(Integer::intValue));
        Double ave = hereList.stream().collect(Collectors.averagingInt(Integer::intValue));
        Integer max = hereList.stream().collect(Collectors.maxBy(Integer::compare)).get();
        Integer min = hereList.stream().collect(Collectors.minBy(Integer::compare)).get();
        System.out.println("sum:"+sum+",ave:"+ave+",max:"+max+",min:"+min);
        // 一次性收集流中的结果，聚合为一个总和，平均值，最大值或最小值的对象
        IntSummaryStatistics summaryStatistics = hereList.stream().collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println(summaryStatistics);
    }

    /**
     * 将结果集收集到Map
     * 当我们希望将集合中的元素收集到Map中时，可以使用Collectors.toMap方法。这个方法有两个参数，用来生成Map的key和value。
     * 【注意】：每个toMap方法，都会有一个对应的toConCurrentMap方法，用来生成一个并发Map。
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void collectToMap(){
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room(11,23,56));
        roomList.add(new Room(22,46,112));
        roomList.add(new Room(44,92,224));
        // 例如将一个Room对象的high作为键width作为值
        Map<Integer,Integer> hwMap = roomList.stream().collect(Collectors.toMap(Room::getHigh, Room::getWidth));
        // 但是通常还是以具体元素作为值的情况多，可以使用Function.identity()来获取实际元素
        Map<Integer,Room> roomMap = roomList.stream().collect(Collectors.toMap(Room::getHigh, Function.identity()));
        // 如果多个元素拥有相同的键，在收集结果时会抛出java.lang.IllegalStateException异常。
        // 可以使用第三个参数来解决，第三个参数用来确定当出现键冲突时，该如何处理结果，如果当出现键冲突时只保留一个并且是保留已经存在的值时，就是如下方式。
        Map<Integer,Room> rMap = roomList.stream()
                .collect(Collectors.toMap(Room::getHigh, Function.identity(),(nowValue,newValue)->nowValue));
        // 如果想指定生成的Map类型，则还需要第四个参数。
        TreeMap<Integer,Room> roomTreeMap = roomList.stream().collect(
                Collectors.toMap(Room::getHigh, Function.identity(),(nowValue,newValue)->newValue,TreeMap::new));
    }

}

