package zhou.wu.mytest.stream.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 并行流
 * Stream的建立，使得并行计算变得容易，但是并行流在使用的时候也是需要注意的。
 * @author Lin.xc
 * @date 2019/10/22
 */
public class StreamParallel {

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
        // 首先，必须是一个并行流，只要在终止方法执行时，流处于并行模式，那么所有的流操作就都会并行执行。
        Stream.of(roomList).parallel();
        // parallel方法可以将任意的串行流转换为一个并行流。其次要确保传递给并行流操作的函数是线程安全的。
        int[] words = new int[23];
        Stream.of(roomList).parallel().forEach(s->{
            if(s.size()<10){
                words[s.size()]++;
            }
        });
        // 上面这个例子中的代码就是错误的，传递给并行流的操作并不是线程安全的。可以改为AtomicInteger的对象数组来作为计数器。
        // 我们使在处理集合数据量较大的时候才能体现出并行流的优势，并且目的是为了在保证线程安全的情况下，提升效率，利用多核CPU的资源。
    }


    /**
     * 小扩展
     * */
    @Test
    @SuppressWarnings("Duplicates")
    public void smallExpand(){
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room(11,23,56));
        roomList.add(new Room(11,84,48));
        roomList.add(new Room(22,46,112));
        roomList.add(new Room(22,75,62));
        roomList.add(new Room(22,56,75));
        roomList.add(new Room(33,92,224));
        // 首先，必须是一个并行流，只要在终止方法执行时，流处于并行模式，那么所有的流操作就都会并行执行。
        IntStream.range(0,roomList.size()).forEach(i->
            System.out.println(roomList.get(i))
        );
    }
}
