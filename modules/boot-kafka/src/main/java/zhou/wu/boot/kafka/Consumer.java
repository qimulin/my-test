package zhou.wu.boot.kafka;

import com.alibaba.fastjson.JSON;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhou.wu
 * @description 消费者
 * @date 2023/4/13
 **/
@Component
public class Consumer {

    @KafkaListener(topics = Constant.TOPIC_MY_TEST)
    public void consume(String message) {
        System.out.println("接收到消息：" + message);
        User user = JSON.parseObject(message, User.class);
        System.out.println("正在为 " + user.getName() + " 办理注册业务...");
        System.out.println("注册成功");
    }

}
