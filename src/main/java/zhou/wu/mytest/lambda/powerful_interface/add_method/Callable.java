package zhou.wu.mytest.lambda.powerful_interface.add_method;

/**
 * Created by lin.xc on 2019/7/19
 * 首先是所有手机都拥有的打电话接口（注意并不是java.util.concurrent.Callable<T>接口）
 */
public interface Callable {
    void call();
}
