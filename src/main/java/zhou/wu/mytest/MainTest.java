package zhou.wu.mytest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import zhou.wu.mytest.web.MailTestDemo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lin.xc
 * @date 2021/3/25
 **/
@Slf4j
public class MainTest {

    public class TestVolatile {
        volatile boolean initialized = false;
        void init() {
            if (initialized) {
                return;
            }
            doInit();
            initialized = true;
        }

        private void doInit() {
        }
    }
//    private static ConcurrentHashMap<Integer, ReentrantLock> locks = new ConcurrentHashMap<>();
//
//    static int x;
//    public static void main(String[] args) {
//        Thread t2 = new Thread(() -> {
//            while (true) {
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("1---"+x);
//                    break;
//                }
//            }
//        }, "t2");
//        t2.start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            x = 10;
//            t2.interrupt();    // 打断t2线程
//        }, "t1").start();
//
//        while (!t2.isInterrupted()) {    // t2线程没被打断，就一直让
//            Thread.yield();
//        }
//        System.out.println("2---"+x);
//    }

    public static void main(String[] args) throws Exception{
//        String json = "{\"arr\":[\"001\",\"002\",\"003\"]}";
//        JSONObject jsonObject = JSON.parseObject(json);
//        System.out.println(jsonObject.getString("arr"));
        String[] strArr = new String[]{
                "select u1.id, u2.id, u2.`id(1)`,u2.`id(1)1`, u3.id, u3.`id(1)` from t_ds_user as u1\n" +
                        "INNER join t_ds_user as u2\n" +
                        "on u1.id = u2.id\n" +
                        "INNER JOIN t_ds_user as u3\n" +
                        "on u1.id = u3.id"
        };
        for (String str:strArr) {
            System.out.println(str+";");
        }
        System.out.println("(( \"dt_bond_demo_zanen_tags\".\"product_id\" in (select \"product_id\" from \"dt_bond_demo_zanen_tags\" where  (\"dt_bond_demo_zanen_tags\".\"turnover_rate\" < 1) and (\"dt_bond_demo_zanen_tags\".tag_engine_partition = (select tag_engine_partition from \"dt_bond_demo_zanen_tags$partitions\" order by tag_engine_partition desc limit 1)))) AND ( \"dt_bond_demo_zanen_tags\".\"product_id\" in (select \"product_id\" from \"dt_bond_demo_zanen_tags\" where  (\"dt_bond_demo_zanen_tags\".\"volume_ratio\" > 3) and (\"dt_bond_demo_zanen_tags\".tag_engine_partition = (select tag_engine_partition from \"dt_bond_demo_zanen_tags$partitions\" order by tag_engine_partition desc limit 1)))))");

    }

    private static boolean check1(){
        System.out.println("check1");
        return true;
    }

    private static int get2(){
        System.out.println("get2");
        return 2;
    }

}


@Data
class Demo{
    private String str;
}
@Data
class SubDemo extends Demo{

}