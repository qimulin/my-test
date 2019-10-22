package zhou.wu.mytest.stream.api;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分组分片
 * 在一个集合中，对具有相同特性的值进行分组是一个很常见的功能，在Stream的API中也提供了相应的方法。
 * @author Lin.xc
 * @date 2019/10/22
 */
public class StreamGroupShard {

    /**
     * 分组
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void groupAndShard(){
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room(11,23,56));
        roomList.add(new Room(11,84,48));
        roomList.add(new Room(22,46,112));
        roomList.add(new Room(22,75,62));
        roomList.add(new Room(22,56,75));
        roomList.add(new Room(33,92,224));
        // 分组
        // 还是上面的例子，将一个Room对象集合按照高度分组。
        Map<Integer,List<Room>> groupMap = roomList.stream().collect(Collectors.groupingBy(Room::getHigh));
        System.out.println("groupMap："+groupMap);
        // 分片
        // 当分类函数是一个返回布尔值的函数时，流元素会被分为两组列表：一组是返回true的元素集合，另一组是返回false的元素集合。
        // 这种情况适用partitoningBy方法会比groupingBy更有效率。
        // 例如我们将房间集合分为两组，一组是高度为22的房间，另一组是其他房间。
        Map<Boolean,List<Room>> partitionMap = roomList.stream()
                .collect(Collectors.partitioningBy(room->room.getHigh()==22));
        System.out.println("partitionMap："+partitionMap);
    }

    /**
     * 扩展功能
     * 面要介绍的这些方法功能，无论是groupingBy方法还是partitioningBy方法都是支持的。
     * optional 翻译：可选择的
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void extension(){
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room(11,23,56));
        roomList.add(new Room(11,84,48));
        roomList.add(new Room(22,46,112));
        roomList.add(new Room(22,75,62));
        roomList.add(new Room(22,56,75));
        roomList.add(new Room(33,92,224));
        // counting方法会返回收集元素的总个数。
        Map<Integer,Long> countMap = roomList.stream()
                .collect(Collectors.groupingBy(Room::getHigh,Collectors.counting()));
        System.out.println("countMap="+countMap);
        // summing(Int|Long|Double)方法接受一个取值函数作为参数，来计算总和。
        Map<Integer,Integer> sumMap = roomList.stream().
                collect(Collectors.groupingBy(Room::getHigh,Collectors.summingInt(Room::getWidth)));
        System.out.println("sumMap="+sumMap);
        // maxBy方法和minBy方法接受比较器作为参数来计算最大值和最小值。
        // 取出分组中宽度最大和最小的房间。
        Map<Integer, Optional<Room>> maxMap = roomList.stream().
                collect(Collectors.groupingBy(Room::getHigh,
                        Collectors.maxBy(Comparator.comparing(Room::getWidth))
                ));
        Map<Integer, Optional<Room>> minMap = roomList.stream().
                collect(Collectors.groupingBy(Room::getHigh,
                        Collectors.maxBy(Comparator.comparing(Room::getWidth))
                ));
        System.out.println("maxMap:"+ JSON.toJSONString(maxMap));
        System.out.println("minMap:"+ JSON.toJSONString(minMap));
        // mapping方法会将结果应用到另一个收集器上。取出分组中宽度最大的房间的宽度
        Map<Integer, Optional<Integer>> collect = roomList.stream().collect(Collectors.groupingBy(Room::getHigh,
                Collectors.mapping(Room::getWidth,
                        Collectors.maxBy(Comparator.comparing(Integer::valueOf)))));
        System.out.println("collect:"+JSON.toJSONString(collect));
        // 无论groupingBy或是mapping函数，如果返回类型是int、long、double都可以将元素收集到一个summarystatistics对象中，
        // 然后从每组的summarystatistics对象中取出函数值的总和、平均值、总数、最大值和最小值。
        Map<Integer,IntSummaryStatistics> summaryStatisticsMap = roomList.stream()
                .collect(Collectors.groupingBy(Room::getHigh,
                        Collectors.summarizingInt(Room::getWidth)));
        System.out.println("summaryStatisticsMap:"+summaryStatisticsMap);
    }

    /**
     * 多级分组
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void multilevelGroup(){
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room(11,23,56));
        roomList.add(new Room(11,84,48));
        roomList.add(new Room(22,46,112));
        roomList.add(new Room(22,75,62));
        roomList.add(new Room(22,56,75));
        roomList.add(new Room(33,92,224));
        // 上面的例子我们都是按一个条件进行的一级分组，其实groupingBy是支持多级分组的。
        // 例如第一级我们将房间按照高度分组，第二级按照宽度分组。
        Map<Integer,Map<Integer,List<Room>>> multistageMap = roomList.stream().collect(
                Collectors.groupingBy(Room::getHigh,Collectors.groupingBy(Room::getWidth)));
        System.out.println("multistageMap:"+JSON.toJSONString(multistageMap));
    }
}
