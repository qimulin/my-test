package zhou.wu.aliyunons.rocketmq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象消费者
 * @author Lin.xc
 * @date 2019/11/18
 */
public abstract class AbstractConsumer {

    private static final Logger log = LoggerFactory.getLogger("MQ");

    protected abstract Consumer getConsumer();

    protected abstract MessageListener getMessageListener();

    /**
     * 定时发送
     * */
    public void subscribe(String topic, String tag){
        if(StringUtils.isBlank(topic)){
            throw new IllegalArgumentException("consumer is null or blank!");
        }
        if(StringUtils.isBlank(tag)){
            throw new IllegalArgumentException("tag is null or blank!");
        }
        getConsumer().subscribe(topic,tag,getMessageListener());
    }

    public void start() {
        getConsumer().start();
    }

    public void shutdown(){
        getConsumer().shutdown();
    }
}
