package zhou.wu.mytest.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lin.xichun on 2019/6/12
 */
public class MyTest {

    public static void main(String[] args) {
        Map<String,String> testMap = new HashMap<>();
        // v6
        testMap.put("1","1");
        testMap.put("2","2");
        testMap.put("3","3");
        Map<String,String> testMap1 = testMap;
        removeField(testMap1);
        System.out.println(testMap.size());
        System.out.println(testMap1.size());
    }

    public static void removeField(Map map){
        map.remove("1");
    }
}
