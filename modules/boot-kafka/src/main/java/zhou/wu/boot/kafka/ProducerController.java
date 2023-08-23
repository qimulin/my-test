package zhou.wu.boot.kafka;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhou.wu
 * @description 生产者
 * @date 2023/4/13
 **/
@RestController
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/test-send")
    public String register(String message) {
        System.out.println("接收到用户信息：" + message);
        kafkaTemplate.send(Constant.TOPIC_MY_TEST, message);
        return "OK";
    }

    @RequestMapping("/test-send-user")
    public String register(User user) {
        String message = JSON.toJSONString(user);
        System.out.println("接收到用户信息：" + message);
        kafkaTemplate.send(Constant.TOPIC_USER, message);
        return "OK";
    }

}
