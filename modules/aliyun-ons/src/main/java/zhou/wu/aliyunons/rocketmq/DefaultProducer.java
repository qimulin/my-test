package zhou.wu.aliyunons.rocketmq;

import com.aliyun.openservices.ons.api.Producer;

/**
 * 默认生产者
 * @author Lin.xc
 * @date 2019/11/18
 */
public class DefaultProducer extends AbstractProducer{

    private Producer producer;

    public DefaultProducer(Producer producer) {
        if (producer == null) {
            throw new IllegalArgumentException("producer is null!");
        }else {
            this.producer = producer;
        }
    }

    @Override
    protected Producer getProducer() {
        return this.producer;
    }
}
