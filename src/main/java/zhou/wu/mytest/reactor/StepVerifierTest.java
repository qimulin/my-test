package zhou.wu.mytest.reactor;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author zhou.wu
 * @description: StepVerifier测试
 * @date 2023/3/9
 **/
public class StepVerifierTest {
    public static void main(String[] args) {
        Flux<String> fruitFlux = Flux
                .just("Apple", "Orange", "Grape", "Banana", "Strawberry");

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }
}
