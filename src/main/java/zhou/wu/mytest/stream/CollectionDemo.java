package zhou.wu.mytest.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin.xc on 2019/7/19
 */
public class CollectionDemo {
    /**
     * 普通的遍历循环
     * */
    @Test
    public void circleEntity(){
        List<String> list = new ArrayList<>();
        list.add("马云");
        list.add("马化腾");
        list.add("李彦宏");
        list.add("雷军");
        list.add("刘强东");
        for (String name : list) {
            System.out.println(name);
        }
    }

    /**
     * 需求：
     * 1)找出姓马的；
     * 2)再从姓马的中找到名字长度等于3的。
     * 如何实现？在Java 8之前的做法可能为：
     * */
    @Test
    public void testWithFor() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("马云");
        list.add("马化腾");
        list.add("李彦宏");
        list.add("雷军");
        list.add("刘强东");
        List<String> maList = new ArrayList<>();
/*        for (String name : list) {
            if (name.startsWith("马")) {
                maList.add(name);
            }
        }
        List<String> length3List = new ArrayList<>();
        for (String name : maList) {
            if (name.length() == 3) {
                length3List.add(name);
            }
        }
        for (String name : length3List) {
            System.out.println(name);
        }*/

        for (String name : list) {
            if (name.startsWith("马")) {
                if(name.length() == 3){
                    System.out.println(name);
                }
            }
        }
    }

    /**
     * Stream用法实现条件过滤
     * */
    @Test
    public void testWithStream() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("马云");
        list.add("马化腾");
        list.add("李彦宏");
        list.add("雷军");
        list.add("刘强东");
        list.stream()
                .filter(s -> s.startsWith("马"))
                .filter(s -> s.length() == 3)
                .forEach(System.out::println);
    }
}
