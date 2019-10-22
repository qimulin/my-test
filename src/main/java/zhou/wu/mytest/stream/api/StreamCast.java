package zhou.wu.mytest.stream.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流的转换
 * @author Lin.xc
 * @date 2019/10/22
 */
public class StreamCast {

    /**
     * filter方法
     * 这是一个Stream的过滤转换，此方法会生成一个新的流，其中包含符合某个特定条件的所有元素。
     * */
    @Test
    public void streamFilter(){
        List<Integer> integerList = new ArrayList();
        integerList.add(15);
        integerList.add(32);
        integerList.add(5);
        integerList.add(232);
        integerList.add(56);
        List<Integer> after = integerList.stream()
                .filter(i->i>50).collect(Collectors.toList());
        System.out.println(after);
    }

    /**
     * map方法
     * map方法指对一个流中的值进行某种形式的转换。需要传递给它一个转换的函数作为参数。
     * */
    @Test
    public void streamMap(){
        List<Integer> integerList = new ArrayList();
        integerList.add(15);
        integerList.add(32);
        integerList.add(5);
        integerList.add(232);
        integerList.add(56);
        //将Integer类型转换成String类型
        List<String> afterString = integerList.stream()
                .map(i->String.valueOf(i)).collect(Collectors.toList());
        System.out.println(afterString);
    }

    /**
     * flatMap方法 flat翻译：平滑的
     * 上面用map方法进行流转换的时候，是对每个元素应用一个函数，并将返回的值收集到一个新的流中。
     * 但是如果有一个函数，它返回的不是一个值，而是一个包含多个值的流。但是你需要的是一个包含多个流中的元素的集合。
     * */
    @Test
    public void streamFlatMap(){
        List<Integer> oneList = new ArrayList();
        List<Integer> twoList = new ArrayList();
        oneList.add(34);
        oneList.add(23);
        oneList.add(87);

        twoList.add(29);
        twoList.add(48);
        twoList.add(92);
        Map<String,List<Integer>> testMap = new HashMap();
        testMap.put("1",oneList);
        testMap.put("2",twoList);
        // 返回的是一个流的集合，但是我需要的是List<Integer>这样一个集合
//        List<Stream<Integer>> testList = testMap.values().stream()
//                .map(number->number.stream()).collect(Collectors.toList());
        // 这个时候就应该使用flatMap将多个流进行合并，然后再收集到一个集合中。自解：平滑地直接操作到两个集合里面的元素
        List<Integer> testList = testMap.values().stream().flatMap(number->number.stream()).collect(Collectors.toList());
    }

    /**
     * limit方法和skip方法
     * */
    @Test
    public void limitAndSkip(){
        List<Integer> myList = new ArrayList();
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);
        myList.add(6);
        // limit(n)方法会返回一个包含n个元素的新的流（若总长小于n则返回原始流）
        List<Integer> afterLimit = myList.stream().limit(4).collect(Collectors.toList());
        System.out.println("afterLimit:"+afterLimit);
        // skip(n)方法正好相反，它会丢弃掉前面的n个元素
        List<Integer> afterSkip = myList.stream().skip(4).collect(Collectors.toList());
        System.out.println("afterSkip："+afterSkip);
        // 用limit和skip方法一起使用就可以实现日常的分页功能
//        List<Integer> pageList = myList.stream()
//                .skip(pageNumber*pageSize).limit(pageSize).collect(Collectors.toList());
    }

    /**
     * distinct方法和sorted方法
     * 上面介绍的流的转换方法都是无状态的。即从一个已经转换的流中取某个元素时，结果并不依赖于之前的元素。
     * 除此之外还有两个方法在转换流时是需要依赖于之前流中的元素的。一个是distinct方法一个是sorted方法。
     * */
    @Test
    public void distinctAndSorted(){
        List<Integer> myTestList = new ArrayList();
        myTestList.add(10);
        myTestList.add(39);
        myTestList.add(10);
        myTestList.add(78);
        myTestList.add(10);
        // distinct方法会根据原始流中的元素返回一个具有相同顺序、去除了重复元素的流，这个操作显然是需要记住之前读取的元素。
        List<Integer> distinctList = myTestList.stream().distinct().collect(Collectors.toList());
        System.out.println("distinctList："+distinctList);
        // sorted方法是需要遍历整个流的，并在产生任何元素之前对它进行排序。因为有可能排序后集合的第一个元素会在未排序集合的最后一位。
        List<Integer> sortList = myTestList.stream().sorted(Integer::compareTo).collect(Collectors.toList());
        System.out.println("sortList："+sortList);
    }
}
