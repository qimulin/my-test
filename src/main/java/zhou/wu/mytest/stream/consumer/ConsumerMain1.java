package zhou.wu.mytest.stream.consumer;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * default Consumer andThen(Consumer<? super T> after)
 * @author Lin.xc
 * @date 2019/9/23
 */
public class ConsumerMain1 {
    public static void main(String[] args) {
        /**
         * 比如将给定的一批用户里面的名称为"lisi"且年龄大于22岁的用户都给打包起来
         * 并有andThen后续操作，从default可以看出该方法是后面加的，肯定是有业务场景需要用到
         * */
        List<Person> lisiList = new ArrayList<>();
        Consumer<Person> consumer  = x -> {
            if (x.getName().equals("lisi")){
                lisiList.add(x);
            }
        };
        consumer = consumer.andThen(
                x -> lisiList.removeIf(y -> y.getAge() < 23)
        );
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
