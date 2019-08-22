package zhou.wu.mytest.lambda.lazy_execution;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by lin.xc on 2019/7/19
 */
@Slf4j
public class LambdaLazyDemo {
    @Test
    public void testLambdaLazy() throws Exception {
        String msgA = "Hello";
        String msgB = "World";
        String msgC = "Java";
        log(1, msgA + msgB + msgC);
        /**
         * SLF4J(应用非常广泛的日志框架)在记录日志时为了解决这种性能浪费的问题，并不推荐首先进行字符串的拼接，
         * 而是将字符串的若干部分作为可变参数传入方法中，仅在日志级别满足要求的情况下才会进行字符串拼接。
         * 其中的大括号{} 为占位符。如果满足日志级别要求，则会将“os”和“windows”两个字符串依次拼接到大括号的位置；否则不会进行字符串拼接。
         * 这也是一种可行解决方案，但Lambda可以做到更好。
         * */
        log.debug("变量{}的取值为{}。", "os", "windows") ;
    }
    private static void log(int level, String msg) {
        if (level == 1) { // 不论条件是否满足,msg都是进行拼接然后传递过来的
            System.out.println(msg);
        }
    }
}
