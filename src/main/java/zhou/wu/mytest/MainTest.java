package zhou.wu.mytest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lin.xc
 * @date 2021/3/25
 **/
@Slf4j
public class MainTest {
    public static void main(String[] args) {
//        String appKey = "3a90057eb4384da290d49b7879103b8e";
////        String requestSecret = args[0];
//        String requestSecret = "f3371eae33664fac9a3c6d777258fbc8";
//        long requestTime = System.currentTimeMillis();
//        String str = appKey + requestSecret + requestTime;
//        String s = DigestUtils.md5DigestAsHex(str.getBytes());
//        log.info("appKey={}", appKey);
//        log.info("requestTime={}", String.valueOf(requestTime));
//        log.info("sign", s.toLowerCase());
//        System.out.println("?requestTime="+String.valueOf(requestTime)
//                +"&appKey="+appKey+"&sign="+s.toLowerCase());
//        JSONObject str = JSON.parseObject("你好");
//        System.out.println(JSON.toJSONString(str));
//        System.out.println("你好吗");
        try {
            System.out.println("try...");
            int num = 1 / 0;
        } catch (Exception e) {
            System.out.println("catch...");
            System.out.println(e.toString());
            return;
        } finally {
            System.out.println("finally...");
        }
    }

}
