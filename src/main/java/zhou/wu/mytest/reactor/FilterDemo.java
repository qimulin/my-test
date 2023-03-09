package zhou.wu.mytest.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author zhou.wu
 * @description 转换和过滤
 * @date 2023/3/9
 **/
public class FilterDemo {
    public static void main(String[] args) {
        /*
         * 【过滤操作】
         * */
        Flux<String> countFlux = Flux.just("one", "two", "three", "four", "five").skip(3);//跳过前三个数据
        //最后countFlux发布的数据为"four"和"five"

        // 靠延迟来过滤
        Flux<String> countFlux2 = Flux.just("one", "two", "three", "four", "five")
                .delayElements(Duration.ofSeconds(1))//每延迟一秒才会发送一个数据，发送第一个数据之前也会被延迟一秒
                .skip(Duration.ofSeconds(4));//跳过前四秒发送的数据
        //最后countFlux中的数据为"four"和"five"

        // 使用 take 过滤数据
        // skip是跳过指定数据，take是取走指定数据
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia")
                .take(3);   // 取走3个
        nationalParkFlux.subscribe(s -> System.out.println(s));
        /*
            运行结果:
            Yellowstone
            Yosemite
            Grand Canyon
        */

        Flux<String> nationalParkFlux2 = Flux.just(
                        "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .delayElements(Duration.ofSeconds(1))   // 每延迟1s才会放出一个元素
                .take(Duration.ofMillis(3500)); // 3.5s（即3500毫秒）后取走元素

        nationalParkFlux2.subscribe(s -> System.out.println(s));
        /*
            运行结果:
            Yellowstone
            Yosemite
            Grand Canyon
        */

        // 使用 filter 进行过滤
        Flux<String> nationalParkFlux3 = Flux.just(
                        "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .filter(np -> !np.contains(" "));//过滤掉表达式为false的值

        /*
            过滤掉包含空格" "的字符串
            最终nationalParkFlux发布的数据为:
            "Yellowstone", "Yosemite", "Zion"
        */

        // 使用 distinct 进行过滤
        Flux<String> animalFlux = Flux.just(
                        "dog", "cat", "bird", "dog", "bird", "anteater")
                .distinct();
        /*
            过滤掉重复的字符串
            最终nationalParkFlux发布的数据为:
            "dog", "cat", "bird", "anteater"
        */
    }
}
