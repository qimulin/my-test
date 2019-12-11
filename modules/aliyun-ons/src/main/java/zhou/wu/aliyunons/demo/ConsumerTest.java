package zhou.wu.aliyunons.demo;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import java.util.Properties;

/**
 * @author Lin.xc
 * @date 2019/11/12
 */
public class ConsumerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // 您在控制台创建的 Group ID
        properties.put(PropertyKeyConst.GROUP_ID, DemoConstant.DEMO_GROUP_ID);
        // 鉴权用 AccessKeyId，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, DemoConstant.ACCESS_KEY);
        // 鉴权用 AccessKeySecret，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, DemoConstant.SECRET_KEY);
        // 设置 TCP 接入域名，进入控制台的实例管理页面，在页面上方选择实例后，在实例信息中的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR,DemoConstant.NAMESRV_ADDR);
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(DemoConstant.DEMO_TOPIC_ID, "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                byte[] body = message.getBody();
                String msg = new String(body);
                System.out.println("Msg Body Str: " + msg);
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}
