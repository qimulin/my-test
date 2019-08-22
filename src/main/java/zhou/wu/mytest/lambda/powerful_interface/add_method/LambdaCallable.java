package zhou.wu.mytest.lambda.powerful_interface.add_method;

/**
 * Created by lin.xc on 2019/7/19
 * 首先是所有手机都拥有的打电话接口（注意并不是java.util.concurrent.Callable<T>接口）
 */
public interface LambdaCallable {
    void call();
    /**
     * 从JDK 1.8开始，可以在接口名称不变的情况下，追加新的方法定义，而对已有的若干实现类不产生任何影响。
     * 这种新添加的方法需要使用default关键字进行修饰并指定方法体实现，被称为“默认方法”。
     * 即实现这个接口的对象不用强制去实现default修饰的方法（注意：这是非抽象方法，必须有方法体）
     * */
    default void autoCall() {
        System.out.println("自动拨号打电话");
    }

    default void photo(){
        System.out.println("Java8不再只有抽象方法啦！");
    }

}
