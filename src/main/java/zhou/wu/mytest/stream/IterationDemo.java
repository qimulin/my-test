package zhou.wu.mytest.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lin.xc on 2019/7/22
 */
public class IterationDemo {

    /**
     * 外部迭代：由迭代器决定迭代的执行，迭代到的位置等等
     * */
    @Test
    public void internalTest() throws Exception {
        System.out.println("=======================外部迭代=========================");
        List<Integer> list = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void externalTest() throws Exception {
        System.out.println("=======================内部迭代=========================");
//        List<Integer> list = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
//        Stream<Integer> stream = list.stream().filter(i -> {
//            System.out.println("内部迭代惰性求值");
//            return i % 2 == 0;
//        });
        List<Integer> list = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        List<Integer> stream = list.stream().filter(i -> i%2==0).collect(Collectors.toList());
        System.out.println("----- 惰性求值 -----");
        stream.forEach(System.out::println);//只有执行终止操作的时候才会真正执行中间操作(惰性求值)
    }
}
