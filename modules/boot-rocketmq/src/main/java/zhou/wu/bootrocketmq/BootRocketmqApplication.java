package zhou.wu.bootrocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 不同的实现，@ComponentScan扫描包的位置不同
 * */
@SpringBootApplication
//@ComponentScan(basePackages = "zhou.wu.bootrocketmq.normal")
@ComponentScan(basePackages = "zhou.wu.bootrocketmq.encapsulation")
public class BootRocketmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootRocketmqApplication.class, args);
    }

}
