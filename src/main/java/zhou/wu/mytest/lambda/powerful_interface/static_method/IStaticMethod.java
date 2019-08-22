package zhou.wu.mytest.lambda.powerful_interface.static_method;

/**
 * Created by lin.xc on 2019/7/19
 * Collection和Collections、File和Files……这些带有s后缀的类从名称上约定为对应的接口或类的工具类。然而这是一种妥协方案，并不是最优方案。
 * 考虑以下几个问题：
 * 1)对于多个对象的公共特征，如何复用？提高其抽象层次。
 * 2)为什么优先实现接口而不是继承父类？因为Java有单继承的限制。
 * 3)对于不依托于对象的内容，应该如何设计？使用静态。
 * 结合这三个问题，JDK 1.8中允许在接口中定义静态方法并指定方法体实现。例如：
 */
public class IStaticMethod {
    static void method() {
        // JDK 1.8中允许在接口中定义静态方法并指定方法体实现。
        System.out.println("静态方法实现！");
    }
}
