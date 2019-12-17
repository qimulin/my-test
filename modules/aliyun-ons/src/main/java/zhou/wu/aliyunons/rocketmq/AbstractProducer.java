package zhou.wu.aliyunons.rocketmq;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author Lin.xc
 * @date 2019/11/18
 * 【注意】：时间单位都是ms（毫秒）
 */
public abstract class AbstractProducer {

    private static final Logger log = LoggerFactory.getLogger("MQ");

    private static final String CHARSET_NAME = "utf-8";

    protected abstract Producer getProducer();

    /**
     * 定时发送
     * */
    public SendResult timeSend(String topic, String tag, Object msgObj, long sendTime){
        return timeSend(topic, tag, null, msgObj, sendTime);
    }

    /**
     * 定时发送
     * */
    public SendResult timeSend(String topic, String tag, String msgKey, Object msgObj, long sendTime){
        return send(topic, tag, msgKey, msgObj, sendTime);
    }

    /**
     * 延时发送
     * */
    public SendResult delaySend(String topic, String tag, Object msgObj, long delayTime){
        return delaySend(topic, tag, null, msgObj, delayTime);
    }

    /**
     * 延时发送
     * */
    public SendResult delaySend(String topic, String tag, String msgKey, Object msgObj, long delayTime){
        long startDeliverTime = System.currentTimeMillis() + delayTime;
        return send(topic, tag, msgKey, msgObj, startDeliverTime);
    }

    /**
     * 消息发送
     * */
    public SendResult send(String topic, String tag, Object msgObj){
        return send(topic, tag, null, msgObj);
    }

    /**
     * 消息发送
     * */
    public SendResult send(String topic, String tag, String msgKey, Object msgObj){
        return send(topic, tag, msgKey, msgObj, null);
    }

    /**
     * 消息发送
     * */
    public SendResult send(String topic, String tag, String msgKey, Object msgObj, Long startDeliverTime){
        Producer producer = this.getProducer();
        assert producer != null;
        String msgStr = JSON.toJSONString(msgObj);
        SendResult sendResult = null;
        try {
            byte[] msgBytes = msgStr.getBytes(CHARSET_NAME);
            Message msg = new Message(topic,tag,msgBytes);
            if(StringUtils.isNotBlank(msgKey)){
                msg.setKey(msgKey);
            }
            if(null!=startDeliverTime){
                // 设置消息需要被投递的时间
                msg.setStartDeliverTime(startDeliverTime);
            }
            log.info("发送消息：topic={}，tags={}，消息内容json={}，消息体message={}", new Object[]{topic, tag, msgStr, msg});
            sendResult = producer.send(msg);
            assert sendResult != null;
        } catch (UnsupportedEncodingException e) {
            log.error("消息发送异常，异常：{}",e.getMessage());

        }
        log.info("消息发送结果：sendResult={}", JSON.toJSONString(sendResult));
        return sendResult;
    }

}
