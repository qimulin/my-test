package zhou.wu.mytest.test;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.*;

/**
 * Created by lin.xichun on 2019/6/12
 */
@Slf4j
public class MyTest {

    public static void main(String[] args) {
//        Map<String,String> testMap = new HashMap<>();
////        System.out.println(Objects.equals("lxc","lxx"));
////        // v6-3
////        testMap.put("1","1");
////        testMap.put("2","2");
////        testMap.put("3","3");
////        Map<String,String> testMap1 = testMap;
////        removeField(testMap1);
////        System.out.println(testMap.size());
////        System.out.println(testMap1.size());
//        BigDecimal b =new BigDecimal("20,001.00");
//        BigDecimal b =readStrAsBigDecimalAndCheckFormat(null);
//        System.out.println(b);
//        Byte flag = new Byte("3");
//        System.out.println(castModifyResultToMchSignStatus(flag));
        retryConfirm(1,5);
    }

    public static void removeField(Map map){
        map.remove("1");
    }

    public static BigDecimal readStrAsBigDecimalAndCheckFormat(String str) {
        DecimalFormat format = new DecimalFormat();
        format.setParseBigDecimal(true);
        ParsePosition position = new ParsePosition(0);
        BigDecimal parse = (BigDecimal) format.parse(str, position);

        if (str.length() == position.getIndex()) {
            return parse;
        }
        return null;
    }

    /**
     * 根据修改结果获取进件状态
     * @author Lin.xc
     * @date 2019-09-26
     * */
    public static String castModifyResultToMchSignStatus(Byte modifyResult){
        switch (modifyResult){
            case 0:
                return "00";
            case 1:
                return "11";
            case 2:
                return "22";
            default:
                return null;
        }
    }

    /**
     * 重试确认进件记录是否为空
     * @author Lin.xc
     * @description
     * */
    private static boolean retryConfirm(int currNum,int maxRetryNum){
        // 如果当前次数小于最大重试次数，则重试查询
        if(currNum<=maxRetryNum){
            log.info("本次重试确认对象是否为空，最大重试次数[{}]，当前次数[{}]",maxRetryNum,currNum);
            // 查询通道状态
            String merchantSign = null;
//            if(currNum==3){
//                merchantSign="lxc";
//            }
            if (merchantSign == null) {
                // 为空，休息3s，再次重试
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return retryConfirm(currNum+1, maxRetryNum);
            }else{
                System.out.println("重试跳出！");
                return false;
            }
        }
        System.out.println("重试完成！");
        return true;
    }
}
