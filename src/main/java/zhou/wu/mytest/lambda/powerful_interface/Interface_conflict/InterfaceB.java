package zhou.wu.mytest.lambda.powerful_interface.Interface_conflict;

/**
 * Created by lin.xc on 2019/7/19
 */
public interface InterfaceB {
    default void method() {
        System.out.println("实现方案B");
    }
}