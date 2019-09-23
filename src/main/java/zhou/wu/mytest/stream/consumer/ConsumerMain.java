package zhou.wu.mytest.stream.consumer;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 只设置accept(T t)
 * @author Lin.xc
 * @date 2019/9/23
 */
public class ConsumerMain {
    public static void main(String[] args) {
        List<Person> lisiList = new ArrayList<>();
        // 比如将给定的一批用户里面的名称为"lisi"的用户都给打包起来
        Consumer<Person> consumer  = x -> {
            if (x.getName().equals("lisi")){
                lisiList.add(x);
            }
        };
        Stream.of(
                new Person(21,"zhangsan"),
                new Person(22,"lisi"),
                new Person(23,"wangwu"),
                new Person(24,"wangwu"),
                new Person(23,"lisi"),
                new Person(26,"lisi"),
                new Person(26,"zhangsan")
        ).forEach(consumer);

        System.out.println(JSON.toJSONString(lisiList));
    }
}
