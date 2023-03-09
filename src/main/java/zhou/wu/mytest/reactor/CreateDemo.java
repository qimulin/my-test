package zhou.wu.mytest.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
public class CreateDemo {
    public static void main(String[] args) {
        /*
        * 【创建操作】
        * */
        // 在使用反应式编程之前，我们得先创建反应式类型的数据，也就是说得先将数据封装为Flux或者是Mono类型的对象
        // Mono
        Mono<String> fruitMono = Mono
                .just("Apple");
        fruitMono.subscribe(f -> System.out.println(f));

        // Flux
        // 根据对象创建
        Flux<String> fruitFlux = Flux
                .just("Apple", "Orange", "Grape", "Banana", "Strawberry");
        /*
        创建好放映室类型的对象以后，就可以开始反应式操作了。
        需要注意的是反应式类型的数据离不开subscribe
        只有被订阅了整个数据才会开始流动起来，操作才会一步一步往下走
        */
        fruitFlux.subscribe(f -> System.out.println(f));

        // 根据数组创建
        String[] fruits = new String[] {
                "Apple", "Orange", "Grape", "Banana", "Strawberry" };

        Flux<String> fruitFluxFromArr = Flux.fromArray(fruits);

        // 根据List创建
        List<String> fruitList = new ArrayList<>();
        fruitList.add("Apple");
        fruitList.add("Orange");
        fruitList.add("Grape");
        fruitList.add("Banana");
        fruitList.add("Strawberry");

        Flux<String> fruitFluxFromList = Flux.fromIterable(fruitList);

        // 根据Java Stream创建
        Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> fruitFluxFromStream = Flux.fromStream(fruitStream);

        // 根据区间生成数据
        //Flux会生成五个数1,2,3,4,5。被订阅以后将会发布这五个数
        Flux<Integer> intervalFluxFromRange = Flux.range(1, 5);

        // 根据时间生成数据
        //每秒钟生成一个数据，从0开始，总共生成五个数据，最终生成的数据为0，1，2，3，4
        Flux<Long> intervalFluxFromInterval = Flux.interval(Duration.ofSeconds(1)).take(5);

        /*
         * 【组合操作】
         * 使用 mergeWith进行合并
         * mergeWith不能保证合并的先后顺序
         * */
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500)); // delayElements(Duration.ofMillis(500))每延迟500ms才发送一个数据
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples")
                .delaySubscription(Duration.ofMillis(250))  // delaySubscription(Duration.ofMillis(250))表示订阅成功后延迟250ms才开始发送数据
                .delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);


    }
}
