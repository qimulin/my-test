package zhou.wu.mytest.lambda;

/**
 * Created by lin.xc on 2019/7/19
 * @FunctionalInterface 表函数式接口
 * 该注解可用于一个接口的定义上, 一旦使用该注解来定义接口，编译器将会强制检查该接口是否确实有且仅有一个抽象方法，否则将会报错。
 */
@FunctionalInterface
public interface TestIntf {
    void test1();
//    String test2(String msg);
}
