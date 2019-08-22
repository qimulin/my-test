package zhou.wu.mytest.lambda.powerful_interface.Interface_conflict;

/**
 * Created by lin.xc on 2019/7/19
 * 接口中的default方法是一个无奈之举，在Java 7及之前要想在定义好的接口中加入新的抽象方法是很困难甚至不可能的，因为所有实现了该接口的类都要重新实现。
 * default方法就是用来解决这个尴尬问题的，直接在接口中实现新加入的方法。
 */
public interface InterfaceA {
    default void method() {
        System.out.println("实现方案A");
    }
    void absMethod();
}
