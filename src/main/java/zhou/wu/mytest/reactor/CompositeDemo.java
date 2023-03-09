package zhou.wu.mytest.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author zhou.wu
 * @description Mono和Flux
 * Reactor有两种核心类型：一种是Mono，一种是Flux。Mono的英文意思是单声道。对比如下两种图可知，
 * Mono处理的数据项不超过一个，也就是所谓的单。而Flux则可以处理一个或者是多个数据项。
 * @date 2023/3/9
 **/
public class CompositeDemo {
    public static void main(String[] args) throws InterruptedException {

        /*
         * 【组合操作】
         * 使用 mergeWith进行合并
         * mergeWith不能保证合并的先后顺序
         * */
        // 使用 mergeWith进行合并
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500)); // delayElements(Duration.ofMillis(500))每延迟500ms才发送一个数据
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples")
                .delaySubscription(Duration.ofMillis(250))  // delaySubscription(Duration.ofMillis(250))表示订阅成功后延迟250ms才开始发送数据
                .delayElements(Duration.ofMillis(500));

        Thread.sleep(10000);
        System.out.println("----- merged -----");
        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);
        System.out.println("----- mergeWith println -----");
        mergedFlux.subscribe(f-> System.out.println(f));

        // 使用 zip 进行合并：zip能保证合并的顺序，依次从两个Flux中分别取出一个数据合并为一个Tuple2类型的数据
        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);
        //打印输出
        System.out.println("----- zip println -----");
        zippedFlux.subscribe(p -> System.out.println(p.getT1() + ";" + p.getT2()));
        /*
            打印结果:
            Garfield ; Lasagna
            Kojak ; Lollipops
            Barbossa ; Apples
        */

        // 使用zip的合并函数就可以使得合并以后的数据并不是Tuple2类型的数据
        //zip(合并源头1, 合并源头2, 合并操作)
        Flux<String> zippedFlux2 = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
        zippedFlux2.subscribe(p -> System.out.println(p));
        /*
            打印结果:
            Garfield eats Lasagna
            Kojak eats Lollipops
            Barbossa eats Apples
        */

        // 使用 first 进行合并
        // first操作将会选择第一个发布消息的Flux。
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");

        Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);

        firstFlux.subscribe(p -> System.out.println(p));
        /*
            由于slowFlux会在订阅的100ms后在开始发布数据，
            因此slowFlux的数据将会比fastFlux的数据出现得晚，
            因此会选择fashFlux中的数据
        */
        /*
            打印结果:
            hare
            cheetah
            squirrel
        */
        System.out.println("----- sleep -----");
        Thread.sleep(60000);


    }
}
