package zhou.wu.mytest.stream.api;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 流的创建
 * @author Lin.xc
 * @date 2019/10/22
 */
public class StreamCreate {
    public static void main(String[] args) {
        // Stream API实现的流程：steam of element→filter→map→sorted→collect，转换成Java代码就是
//        Integer transactionsIds =
//                roomList.stream()
//                        .filter(b -> b.getLength() == 10)
//                        .sorted((x,y) -> x.getHigh() - y.getHigh())
//                        .mapToInt(Room::getWidth).sum();
    }

    /************************************* 从遍历到Stream操作 *****************************************/
    /**
     * 从遍历到Stream操作
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void helloStream(){
        // 从遍历到Stream操作
        List<Integer> numbers = new ArrayList<>();
        numbers.add(3);
        numbers.add(4);
        numbers.add(8);
        numbers.add(16);
        numbers.add(19);
        numbers.add(27);
        numbers.add(23);
        numbers.add(99);
        numbers.add(15);
        numbers.add(32);
        numbers.add(5);
        numbers.add(232);
        numbers.add(56);
        int count = 0;
        for(Integer i:numbers){
            if(i>20){
                count++;
            }
        }
        System.out.println("count："+count);
        // 如上遍历的代码转换成使用Stream的API来实现如下，正常的遍历用Stream一行就可以实现了。
        long sCount = numbers.stream().filter(i->i>20).count();
        System.out.println("sCount："+sCount);
    }

    /************************************* 创建Stream *****************************************/
    /**
     * Arrays.stream()
     * 当在日常编程中面对的是一个数组，也可以使用Arrays.stream()方法来使用Stream
     * */
    @Test
    public void arraysStream(){
        Integer[] array = new Integer[]{3,4,8,16,19,27,23,99,76,232,33,96};
        long count = Arrays.stream(array).filter(i->i>20).count();
        System.out.println("count："+count);
    }

    /**
     * Stream.of()
     * 当面对数组时除了可以使用Arrays.stream()方法外，还可以使用Stream将需要的数组转成Stream。
     * 这个方法不但支持传入数组，将数组转成Stream，也支持传入多个参数，将参数最终转成Stream
     * 其实Stream.of()也是调用的Arrays.stream()方法来实现的。
     * */
    @Test
    public void streamOf(){
        Integer[] array = new Integer[]{3,4,8,16,19,27,23,99,76,232,33,96};
        long count = Stream.of(array).filter(i->i>20).count();
        long sum = Stream.of(12,77,59,3,654).filter(i->i>20).mapToInt(Integer::intValue).sum();
        System.out.println("count："+count+",sum:"+sum);
    }

    /**
     * Stream.generate()
     * Stream接口有两个用来创建无限Stream的静态方法。generate()方法接受一个参数函数，可以使用类似如下代码来创建一个你需要的Stream。
     * */
    @Test
    public void streamGenerate(){
        Stream<String> stream = Stream.generate(() -> "test").limit(10);
        String[] strArr = stream.toArray(String[]::new);
        System.out.println(Arrays.toString(strArr));
    }

    /**
     * Stream.iterate()
     * Stream接口的另一用来创建无限Stream的静态方法就是iterate()方法。
     * iterate()方法也是接受一个参数函数，可以用类似如下代码来创建一个你需要的Stream。
     * */
    @Test
    public void streamIterate(){
        Stream<BigInteger> bigIntStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.TEN)).limit(10);
        BigInteger[] bigIntArr = bigIntStream.toArray(BigInteger[]::new);
        System.out.println(Arrays.toString(bigIntArr));
    }

    /**
     * Collection.stream()
     * 这个就是最常见的Stream了。因为Collection是Java中集合接口的父接口，Java中的集合都继承或实现了此接口。
     * 所以Java中的集合都可以使用此方法来创建一个Stream
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void collectionStream(){
        List<Integer> numbers = new ArrayList<>();
        numbers.add(3);
        numbers.add(4);
        numbers.add(8);
        numbers.add(16);
        numbers.add(19);
        numbers.add(27);
        numbers.add(23);
        Spliterator<Integer> integers = numbers.spliterator();
        StreamSupport.stream(integers,false).forEach(number-> {
            System.out.println(number);
        });
    }
}
