package zhou.wu.mytest.reactor;

import reactor.core.publisher.Mono;

/**
 * @author zhou.wu
 * @description: Demo1
 * @date 2023/3/9
 **/
public class Demo1 {
    // 以下两种方式都能达到一样的效果
    public static void main(String[] args) {
        // 命令式编程
        String name = "Simon";
        String capitalName = name.toUpperCase();
        String greeting = "Hello " + capitalName + "!";
        System.out.println(greeting);

        // 反应式编程
        Mono.just("Simon")
                .map(n -> n.toUpperCase())  // 对just中的Simon改成大写
                .map(cn -> "Hello " + cn + "!") // 对大写后的SIMON加前后拼接
                .subscribe(gn -> System.out.println(gn));   // 最后打印

    }
}
