package zhou.wu.mytest.lambda.powerful_interface.add_method;

/**
 * Created by lin.xc on 2019/7/19
 * 苹果的iPhone手机
 */
public class LambdaIPhone implements LambdaCallable {
    @Override
    public void call() {
        System.out.println("用iPhone手机打电话");
    }
}