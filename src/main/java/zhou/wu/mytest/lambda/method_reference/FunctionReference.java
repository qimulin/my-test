package zhou.wu.mytest.lambda.method_reference;

import org.junit.Test;
import zhou.wu.mytest.lambda.filter.Product;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by lin.xc on 2019/7/19
 */
public class FunctionReference {
    private static void printString(Printable p) {
        p.print("Hello, you!");
        p.print("Hello, World!");   // printString定义Printable对象传入的时候，调用其方法print，print还没有具体实现
    }
    public static void main(String[] args) {
        // 一般写法
//        printString(new Printable(){    // 传入Printable对象时定义其print方法的实现->System.out.println传入方法的参数
//            @Override
//            public void print(String str) {
//                System.out.println(str);
//            }
//        });
//        // Lambda普通写法
//        printString(s -> System.out.println(s));
        // Lambda方法引用
        /**
         * 双冒号:: 为引用运算符，而它所在的表达式被称为方法引用。
         * 如果Lambda要表达的函数方案已经存在于某个方法的实现中，那么则可以通过双冒号来引用该方法作为Lambda的替代者。
         * 自解：既然我Printable要做的事只是System.out.println(参数)，那我直接将参数全部交给PrintStream（System.out）对象去调用它的println方法，
         * 即PrintStream的println方法可以帮我完成这个操作
         * 即Printable具体的传入System.out::println去实现该方法
         * */
        printString(System.out::println);
        // 调用对象实例方法
        MethodA mA = new MethodA();
        printString(mA::output);
        // 调用静态方法
        printString(StaticMethodB::output);
        // 调用静态属性中的方法
        printString(StaticPropertyMethodA.methodA::output);
    }

    @Test
    public void test1() throws Exception {
        // 1.对象的引用 :: 实例方法名
        //Arrays.asList(1,2,3).forEach((i) -> System.out.println(i));
        PrintStream printStream = System.out;
        Arrays.asList(1,2,3).forEach(c-> System.out.println(c));
        Arrays.asList(1,2,3).forEach(printStream::println);
        Arrays.asList(1,2,3).forEach(System.out::println);
        System.out.println("================================================");

        // 2.类名 :: 静态方法名
        //Supplier<Double> s = () -> Math.random();
        Supplier<Double> s = Math::random;
        System.out.println(s.get());
        System.out.println("================================================");

        // 3.类名 :: 实例方法名
        //Function<Product,String> f = (p) -> p.getName();
        Function<Product,String> f = Product::getName;
        String name = f.apply(new Product(1L, "锤子手机", 999.99,"手机"));
        System.out.println(name);
        System.out.println("================================================");

        // 4.类名 :: new
        //Supplier<Product> sp = ()-> new Product();
        Supplier<Product> sp = Product::new;
        Product product = sp.get();
        System.out.println(product);
        System.out.println("================================================");

        // 5.类型[] :: new
        Function<Integer,String[]> f2 = String[]::new;
        String[] strings = f2.apply(20);
        System.out.println(strings.length);
    }
}
