package zhou.wu.mytest.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhou.wu
 * @description 逻辑操作
 * @date 2023/3/9
 **/
public class LogicOperateDemo {
    public static void main(String[] args) {
        // all 逻辑操作
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));//animalFlux发布的每一个数据是否都包含了字母'a'?(注意：需要全部)
        hasAMono.subscribe(System.out::println);

        Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));//animalFlux发布的数据中不是都包含字母'k'，故返回false
        hasKMono.subscribe(System.out::println);

        // any 逻辑操作
        Mono<Boolean> anyMono = animalFlux.any(a -> a.contains("t"));//animalFlux发布的数据中是否有一个数据包含了字母't'
        anyMono.subscribe(System.out::println);
        /*
        打印结果:
        true
        */

    }
}
