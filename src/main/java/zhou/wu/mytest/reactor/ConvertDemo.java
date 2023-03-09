package zhou.wu.mytest.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;

/**
 * @author zhou.wu
 * @description 转换操作
 * @date 2023/3/9
 **/
@Slf4j
public class ConvertDemo {

    private static class Player {
        private final String firstName;
        private final String lastName;

        public Player(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "firstName:"+firstName + ",lastName:" + lastName;
        }
    }

    /**
     * 使用 map 进行转换
     * map操作实际上是创建了一个新的Flux对象，不过在创建新的Flux对象之前会先进行转换操作，并且map操作是同步进行的。
     * */

    @Test
    public void map() {
        Flux<Player> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(n -> {
                    String[] split = n.split("\\s");// "\s"表示空格
                    return new Player(split[0], split[1]);
                });
        //转换以后playerFlux中就包含了三个player对象
        playerFlux.subscribe(f -> log.info("player = {}", f));
    }

    /**
     * 使用 flatMap 进行转换
     * flatMap并不像map操作那样简单地将一个对象转化为另一个对象，而是将对象转化为新的Mono或者是Flux对象。得到的Mono或者是Flux对象会扁平化为新的Flux对象。
     * */
    @Test
    public void flatMap() {
        Flux<Player> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Mono.just(n)
                        .map(p -> {
                            String[] split = p.split("\\s");
                            return new Player(split[0], split[1]);
                        })
                        .subscribeOn(Schedulers.parallel())//表示每个订阅都应该在并行的线程中进行处理
                );
        playerFlux.subscribe(f ->log.info("player = {}",f));
        /*
        * 日志打印输出
        * subscribeOn(Schedulers.parallel())指定了每个订阅都应该在并行的线程中进行处理，所以最终得到的数据的先后顺序和输入时的顺序不一定一致。
        * */
    }
    /**
     * subscribeOn() 的名称与 subscribe() 类似，但它们却截然不同。subscribe() 是一个动词，它订阅一个响应式流并有效地将其启动，而subscribeOn()则更具描述性，它指定了应该如何并发地处理订阅。
     * 在本例中，使用了Schedulers.parallel()，它是使用固定大小线程池的工作线程（大小与 CPU 内核的数量一样）。但是调度程序支持多个并发模型，如下表所示
     * Schedulers 方法	描述
     * .immediate()	在当前线程中执行订阅
     * .single()	在单个可重用线程中执行订阅，对所有调用方重复使用同一线程
     * .newSingle()	在每个调用专用线程中执行订阅
     * .elastic()	在从无限弹性池中提取的工作进程中执行订阅，根据需要创建新的工作线程，并释放空闲的工作线程（默认情况下 60 秒）
     * .parallel()	在从固定大小的池中提取的工作进程中执行订阅，该池的大小取决于 CPU 核心的数量。
     * */

    /**
     * 将数据缓存到多个List中
     * */
    @Test
    public void toMultiList() {
        Flux<String> fruitFlux = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");
        //每三个元素组成一个List<String>集合，然后封装到新的Flux对象中
        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
        bufferedFlux.subscribe(s -> log.info("bufferedFlux = {}", s));
    }

    /**
     * 将数据缓存到一个List中
     * */
    @Test
    public void toList() {
        Flux<String> fruitFlux = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");

        Mono<List<String>> fruitListMono = fruitFlux.collectList();
        fruitListMono.subscribe(s -> s.forEach(System.out::println));
    }

    /**
     * 将数据缓存到一个 Map 中
     * */
    @Test
    public void toMap() {
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Map<Character, String>> animalMapMono =
                animalFlux.collectMap(a -> a.charAt(0));
        animalMapMono.subscribe(s -> s.forEach((key, value) -> System.out.println("key = " + key + " ; " + "value = " + value)));
        /*
            打印结果:
            key = a ; value = aardvark
            key = e ; value = eagle
            key = k ; value = kangaroo
        */
    }

}


