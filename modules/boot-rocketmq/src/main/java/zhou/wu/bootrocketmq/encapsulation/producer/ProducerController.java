package zhou.wu.bootrocketmq.encapsulation.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zhou.wu.bootrocketmq.content.UserContent;

import java.util.List;

@RestController
public class ProducerController {
    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;


    /**
     * 发送普通消息
     */
    @GetMapping("/sendMessage")
    public void sendMsg() {

        for(int i=0;i<100;i++){
            UserContent user = new UserContent();
            user.setUsername("test"+i);
            user.setPwd(String.valueOf(i));
            String json = JSON.toJSONString(user);
            Message msg = new Message("user-topic","white",json.getBytes());
            try {
                SendResult result = defaultProducer.send(msg);
                System.out.println("消息id:"+result.getMsgId()+":"+","+"发送状态:"+result.getSendStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 发送事务消息
     * @return
     */
    @GetMapping("/sendTransactionMess")
    public String sendTransactionMsg() {
        SendResult sendResult = null;
        try {
            // a,b,c三个值对应三个不同的状态
            String ms = "c";
            Message msg = new Message("user-topic","white",ms.getBytes());
            // 发送事务消息
            sendResult = transactionProducer.sendMessageInTransaction(msg, (Message msg1, Object arg) -> {
                String value = "";
                if (arg instanceof String) {
                    value = (String) arg;
                }
                if (value == "") {
                    throw new RuntimeException("发送消息不能为空...");
                } else if (value =="a") {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (value =="b") {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }, 4);
            System.out.println(sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult.toString();
    }

    /**
     * 支持顺序发送消息
     */
    @GetMapping("/sendMessOrder")
    public void sendMsgOrder() {
        for(int i=0;i<100;i++) {
            UserContent user = new UserContent();
            user.setUsername("test" + i);
            user.setPwd(String.valueOf(i));
            String json = JSON.toJSONString(user);
            Message msg = new Message("user-topic", "white", json.getBytes());
            try{
                defaultProducer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        int index = ((Integer) arg) % mqs.size();
                        return mqs.get(index);
                    }
                },i);
            }
            catch (Exception e){
                e.printStackTrace();
            }
//            "{\"AutoSettlementPeriod\":\"T1\",\"AutoFeeEffectType\":\"NOW\",\"AutoAppFeeMode\":\"DEFAULT\",\"AutoFeePurpose\":\"DEFAULT\",\"AutoValue\":\"OWN_CASHACCOUNT\",\"AutoMoneyPeriod\":\"T1\",\"AutoCalcType\":\"RATIO\",\"AutoBankCardFront\":\"14a23cd9-6724-4065-a34c-d518c14d42d5\",\"AutoBankCardNo\":\"6212261202030007067\",\"AutoBranchName\":\"工商银行\",\"AutoBranchProvince\":\"120000000\",\"AutoBranchCity\":\"120300000\",\"AutoBankBranchFullName\":\"中国工商银行股份有限公司杭州西兴支行\",\"AutoBankReservedTel\":\"13222222222\",\"AutoContactLine\":\"102331002315\",\"AutoWeChatFee\":\"0.4\",\"AutoAliFee\":\"0.38\",\"AutoCloudPayFeeOne\":\"0.38\",\"AutoCloudPayFeeTwo\":\"0.55\"}"
        }
    }
}
