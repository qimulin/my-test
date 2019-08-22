package zhou.wu.mytest.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by lin.xc on 2019/7/22
 * 处理流
 */
public class ProcessStream {
    /**
     * 筛选和切片
     * */
    @Test
    public void test1() throws Exception {
        Arrays.asList(1, 2, 1, 3, 3, 2, 4).stream().filter(i -> i % 2 == 0).forEach(System.out::println);
        System.out.println("================================================");
        Arrays.asList(1, 2, 1, 3, 3, 2, 4).stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);
        System.out.println("================================================");
        Arrays.asList(1, 2, 1, 3, 3, 2, 4).stream().distinct().forEach(System.out::println);
        System.out.println("================================================");
        Arrays.asList(1, 2, 1, 3, 3, 2, 4).stream().distinct().limit(2).forEach(System.out::println);
        System.out.println("================================================");
        Arrays.asList(1, 2, 1, 3, 3, 2, 4).stream().distinct().skip(2).forEach(System.out::println);
    }

    /**
     * 映射
     * */
    @Test
    public void test3() throws Exception {
        System.out.println("=======================map=========================");
        Stream<String> stream = Stream.of("i","love","java");
        stream.map(s -> s.toUpperCase()).forEach(System.out::println);
        System.out.println("=======================flatMap========================");
        Stream<List<String>> stream2 = Stream.of(Arrays.asList("H","E"), Arrays.asList("L", "L", "O"));
        stream2.flatMap(list -> list.stream()).forEach(System.out::println);
    }

    /**
     * 排序
     * */
    @Test
    public void test4() throws Exception {
        System.out.println("====================自然排序============================");
        Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().sorted().forEach(System.out::println);
        System.out.println("====================定制排序============================");
        Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().sorted((x,y) -> y.compareTo(x)).forEach(System.out::println);
    }


}
