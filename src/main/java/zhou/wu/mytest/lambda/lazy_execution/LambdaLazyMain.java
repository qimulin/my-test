package zhou.wu.mytest.lambda.lazy_execution;

import org.junit.Test;

import java.text.DecimalFormat;

/**
 * Created by lin.xc on 2019/7/19
 */
public class LambdaLazyMain {
    @Test
    public void testLambdaLazy() throws Exception {
        String msgA = "Hello";
        String msgB = "World";
        String msgC = "Java";
//        String[] msg = new String[]{msgA,msgB,msgC};
        log(1, msgA + msgB + msgC);

        log(1, (msg) -> msgA + msgB + msgC);

        log(2, (msg) -> {
            System.out.println("Lambda执行！");//这句话没有被输出,说明Lambda是延迟执行的
            return msgA + msgB + msgC;
//            return msg[0] + msg[1] + msg[2];
        });
    }

    private static void log(int level, String msg) {
        if (level == 1) {//不论条件是否满足,msg都是进行拼接然后传递过来的
            System.out.println(msg);
        }
    }

    private static void log(int level, MessageBuilder builder) {
        if (level == 1) {//只有当条件满足才会执行Lambda表达式中的拼接
            System.out.println(builder.buildMessage());
        }
    }

    public static void main(String[] args) {
//        double auxiliaryValue = Math.pow(10, 2);
//        double auxiliaryValue = 200.01;
//        String auxiliaryValueStr = new DecimalFormat("0").format(auxiliaryValue);
//        Double aDouble = Double.valueOf(auxiliaryValue);
        Integer auxiliaryValue = Double.valueOf(Math.pow(10, 3)).intValue();
        String result = String.format("CEIL(%s*%d)/%d","c1",auxiliaryValue,auxiliaryValue);
        System.out.println(result);
    }
}

@FunctionalInterface
interface MessageBuilder {
    String buildMessage(String... msgs);
}
