package zhou.wu.mytest.guava.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

/**
 * 布隆过滤器尝试
 * @author zhou.wu
 * @date 2023/9/26
 **/
public class BloomFilterTest {

    private static int size = 1000000;

    /**
     * 在Guava库中，Funnels类提供了一些预定义的Funnel实现，用于将不同类型的数据转化为位数组的操作。
     * 在这种情况下，Funnels.integerFunnel()返回一个Funnel对象，该对象用于将整数类型的数据转化为位数组。
     * */
    private static BloomFilter<Integer> bloomFilter =BloomFilter.create(Funnels.integerFunnel(), size);

    public static void main(String[] args) {        
        for (int i = 0; i < size; i++) {
            // 把0~(size-1)加到bloomFilter中
            bloomFilter.put(i);
        }        

        List<Integer> list = new ArrayList<>(1000);
        // 故意取10000个不在过滤器里的值，看看有多少个会被认为在过滤器里
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("误判的数量：" + list.size());
    }
}
