package zhou.wu.aliyunons.rocketmq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认消费者
 * @author Lin.xc
 * @date 2019/11/18
 */
public class DefaultConsumer extends AbstractConsumer{

    private ConsumerBean consumer;

    private MessageListener messageListener;

    public DefaultConsumer(ConsumerBean consumer, MessageListener messageListener) {
        if (consumer == null) {
            throw new IllegalArgumentException("consumer is null!");
        }else if (messageListener == null) {
            throw new IllegalArgumentException("messageListener is null!");
        }else {
            this.consumer = consumer;
            this.messageListener = messageListener;
        }
    }

    @Override
    public void subscribe(String topic, String tag){
        //订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>(1);
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setExpression(tag);
        subscriptionTable.put(subscription, this.messageListener);
        //订阅多个topic模仿上面设置添加
        this.consumer.setSubscriptionTable(subscriptionTable);
    }

    @Override
    protected Consumer getConsumer() {
        return this.consumer;
    }

    @Override
    protected MessageListener getMessageListener() {
        return this.messageListener;
    }
}
