package zhou.wu.mytest.lambda.powerful_interface.add_method;

/**
 * Created by lin.xc on 2019/7/19
 * 三星的Galaxy手机
 */
public class LambdaGalaxy implements LambdaCallable {
    @Override
    public void call() {
        System.out.println("用Galaxy手机打电话");
    }
}