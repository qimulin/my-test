package zhou.wu.mytest.mockito;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhou.wu
 * @description 用于测试Mock静态方法
 * @date 2023/6/7
 **/
public class StaticUtils {

    /**
     * 返回指定区间的 Integer List
     * */
    public static List<Integer> range(int start, int end) {
        return IntStream.range(start, end).boxed().collect(Collectors.toList());
    }

    /**
     * 返回 Echo 字符串
     * */
    public static String name() {
        return "Echo";
    }

    public static void main(String[] args) {
        List<Integer> range = range(2, 6);
    }
}
