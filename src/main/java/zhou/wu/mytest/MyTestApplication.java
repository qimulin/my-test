package zhou.wu.mytest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("zhou.wu.mytest.web.dao")
public class MyTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTestApplication.class, args);
    }

}
