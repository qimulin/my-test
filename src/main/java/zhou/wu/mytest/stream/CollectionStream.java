package zhou.wu.mytest.stream;

import org.junit.Before;
import org.junit.Test;
import zhou.wu.mytest.lambda.filter.Product;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lin.xc on 2019/7/22
 * 收集流
 */
public class CollectionStream {
    /**
     * 查找匹配
     * */
    @Test
    public void test5() throws Exception {
        System.out.println("======================检查是否匹配所有==========================");
        boolean allMatch = Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().allMatch(x-> x>0);
        System.out.println(allMatch);
        System.out.println("======================检查是否至少匹配一个元素====================");
        boolean anyMatch = Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().anyMatch(x -> x>7);
        System.out.println(anyMatch);
        System.out.println("======================检查是否没有匹配的元素======================");
        boolean noneMatch = Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().noneMatch(x -> x >10);
        System.out.println(noneMatch);
        System.out.println("======================返回第一个元素==========================");
        Optional<Integer> first = Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().findFirst();
        System.out.println(first.get());
        System.out.println("======================返回当前流中的任意元素=======================");
        Optional<Integer> any = Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().findAny();
        System.out.println(any.get());
    }

    /**
     * 统计
     * */
    @Test
    public void test6() throws Exception {
        long count = Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().count();
        System.out.println(count);
        Optional<Integer> max = Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().max((x,y) -> x.compareTo(y));
        System.out.println(max.get());
        Optional<Integer> min = Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().min((x,y) -> x.compareTo(y));
        System.out.println(min.get());
    }

    /**
     * 归约
     * */
    @Test
    public void test7() throws Exception {
        System.out.println("=====reduce:将流中元素反复结合起来，得到一个值==========");
        Stream<Integer> stream = Stream.iterate(1, x -> x+1).limit(100);
        //stream.forEach(System.out::println);
        Integer sum = stream.reduce(0,(x,y)-> x+y);
        System.out.println(sum);
    }

    /**
     * 汇总
     * */
    @Test
    public void test8() throws Exception {
        System.out.println("=====collect:将流转换为其他形式:list");
        List<Integer> list = Stream.iterate(1, x -> x+1).limit(100).collect(Collectors.toList());
        System.out.println(list);
        System.out.println("=====collect:将流转换为其他形式:set");
        Set<Integer> set = Arrays.asList(1, 1, 2, 2, 3, 3, 3).stream().collect(Collectors.toSet());
        System.out.println(set);
        System.out.println("=====collect:将流转换为其他形式:TreeSet");
        TreeSet<Integer> treeSet = Arrays.asList(1, 1, 2, 2, 3, 3, 3).stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(treeSet);
        System.out.println("=====collect:将流转换为其他形式:map");
        Map<Integer, Integer> map = Stream.iterate(1, x -> x+1).limit(100).collect(Collectors.toMap(Integer::intValue, Integer::intValue));
        System.out.println(map);
        System.out.println("=====collect:将流转换为其他形式:sum");
        Integer sum = Stream.iterate(1, x -> x+1).limit(100).collect(Collectors.summingInt(Integer::intValue));
        System.out.println(sum);
        System.out.println("=====collect:将流转换为其他形式:avg");
        Double avg = Stream.iterate(1, x -> x+1).limit(100).collect(Collectors.averagingInt(Integer::intValue));
        System.out.println(avg);
        System.out.println("=====collect:将流转换为其他形式:max");
        Optional<Integer> max = Stream.iterate(1, x -> x+1).limit(100).collect(Collectors.maxBy(Integer::compareTo));
        System.out.println(max.get());
        System.out.println("=====collect:将流转换为其他形式:min");
        Optional<Integer> min = Stream.iterate(1, x -> x+1).limit(100).collect(Collectors.minBy((x,y) -> x-y));
        System.out.println(min.get());
    }

    private List<Product> products = new ArrayList<>();

    @Before
    public void init() {
        products.add(new Product(1L, "苹果手机", 8888.88,"手机"));//注意:要给Product类加一个分类名称dirName字段
        products.add(new Product(2L, "华为手机", 6666.66,"手机"));
        products.add(new Product(3L, "联想笔记本", 7777.77,"电脑"));
        products.add(new Product(4L, "机械键盘", 999.99,"键盘"));
        products.add(new Product(5L, "雷蛇鼠标", 222.22,"鼠标"));
    }

    @Test
    public void test9() throws Exception {
        System.out.println("=======根据商品分类名称进行分组==========================");
        Map<String, List<Product>> map = products.stream().collect(Collectors.groupingBy(Product::getDirName));
        System.out.println(map);
        System.out.println("=======根据商品价格范围多级分组==========================");
        Map<Double, Map<String, List<Product>>> map2 = products.stream().collect(Collectors.groupingBy(
                Product::getPrice, Collectors.groupingBy((p) -> {
                    if (p.getPrice() > 1000) {
                        return "高级货";
                    } else {
                        return "便宜货";
                    }
                })));
        System.out.println(map2);

    }

    @Test
    public void test10() throws Exception {
        System.out.println("========根据商品价格是否大于1000进行分区========================");
        Map<Boolean, List<Product>> map = products.stream().collect(Collectors.partitioningBy(p -> p.getPrice() > 1000));
        System.out.println(map);
    }
}
