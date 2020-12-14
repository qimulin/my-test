package zhou.wu.mytest.lambda.inner_function;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by lin.xc on 2019/7/19
 * 常用的内置函数式接口
 */
public class LambdaFunction {
    //  函数式接口类型  参数类型  返回类型  说明
    //  ConsumerMain<T>消费型接口  T void  对类型为T的对象操作，方法：void accept(T t)
    @Test
    public void test1() throws Exception {
        shop(10000.0, new Consumer<Double>() {
            @Override
            public void accept(Double money) {
                System.out.println("购买电脑花费:" + money + "元");
            }
        });
        shop(5000D, money-> System.out.println("购买手机花费:" + money + "元"));
    }

    public static void shop(Double money, Consumer<Double> con) {
        con.accept(money);
    }
    //===================================================================================
    //  函数式接口类型  参数类型  返回类型 说明
    //  Supplier<T>供给型接口  无  T    返回类型为T的对象，方法：T get();可用作工厂
    @Test
    public void test2() throws Exception {
        String code = getCode(4, ()-> new Random().nextInt(10));
        System.out.println(code);
    }
    //获取指定位数的验证码
    public String getCode(int num, Supplier<Integer> sup){
        String str = "";
        for (int i = 0; i <num ; i++) {
            str += sup.get();
        }
        return str;
    }

    //===================================================================================
    //  函数式接口类型  参数类型  返回类型  说明
    //  Function<T, R>函数型接口  T  R  对类型为T的对象操作，并返回结果是R类型的对象。方法：R apply(T t);
    @Test
    public void test3() throws Exception {
        int length = getStringRealLength(" www.wolfcode.cn  ", str -> str.trim().length());
        System.out.println(length);

    }
    public int getStringRealLength(String str, Function<String,Integer> fun){
        return fun.apply(str);
    }

    //===================================================================================
    //  函数式接口类型  参数类型  返回类型  说明
    //  Predicate<T>断言型接口  T  boolean  判断类型为T的对象是否满足条件，并返回boolean 值。方法boolean test(T t);
    @Test
    public void test4() throws Exception {
        List<String> list = getString(Arrays.asList("Java8","Lambda","wolfcode"), s -> s.length() > 5);
        System.out.println(list);
        //后面学习了Stream API操作更简单
        Arrays.asList("Java8","Lambda","wolfcode").stream().filter(s -> s.length() > 5).forEach(System.out::println);

    }
    //获取集合中长度大于5的字符串
    public List<String> getString(List<String> list, Predicate<String> pre){
        List<String> newList = new ArrayList<>();
        for (String string : list) {
            if (pre.test(string)) {
                newList.add(string);
            }
        }
        return newList;
    }

    /**
     * 两List判重
     * */
    private <T> boolean checkDuplicate(List<T> paramList1, List<T> paramList2, Function<T, String> f) {
        List<String> stringList = paramList1.stream().map(f).collect(Collectors.toList());
        List<String> stringList2 = paramList2.stream().map(f).collect(Collectors.toList());
        stringList.addAll(stringList2);
        long count = stringList.stream().distinct().count();
        if (stringList.size() == count) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 多List判重
     * */
    private <T> boolean checkDuplicate(Function<T, String> f, List<T>... paramLists) {
        List<String> stringList = new ArrayList<>();
        for(List<T> paramList: paramLists){
            // 防止stream报空指针
            if(null!=paramList){
                stringList.addAll(paramList.stream().map(f).collect(Collectors.toList()));
            }
        }
        long count = stringList.stream().distinct().count();
        if (stringList.size() == count) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        LambdaFunction lambdaFunction = new LambdaFunction();
        lambdaFunction.checkDuplicate(null,null,Person::getName);
    }

    @Data
    class Person{
        Integer id;
        String name;
    }
}
