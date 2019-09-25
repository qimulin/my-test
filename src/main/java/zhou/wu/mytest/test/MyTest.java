package zhou.wu.mytest.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.*;

/**
 * Created by lin.xichun on 2019/6/12
 */
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
}
