package zhou.wu.mytest.lambda.powerful_interface.add_method;

import org.junit.Test;

/**
 * 有了接口，我们在打电话的时候就可以屏蔽掉不同手机之间的差别，只专注于打电话功能即可
 * Created by lin.xc on 2019/7/19
 */
public class LambdaPowerfulInterface {
    private static void usePhone(LambdaCallable phone) {
        phone.autoCall();
    }
    /**
     * 想像一下如果Callable接口除了普通的call方法之外，还希望引入一个autoCall（自动拨号）方法，那怎么办？
     * 如果继续添加一个抽象方法，那么已经使用Callable接口的两个实现类都必须进行代码修改以进行覆盖重写。
     * 然而问题是，接口的制定者往往在交付接口进行使用之后，无法控制其使用者再“追加协议内容”。这就破坏了接口的向后兼容性。
     * */

    @Test
    public void testPhone() throws Exception {
        usePhone(new LambdaIPhone());
        usePhone(new LambdaGalaxy());
    }
}
