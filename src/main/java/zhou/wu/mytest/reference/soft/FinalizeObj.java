package zhou.wu.mytest.reference.soft;

/**
 * @author zhou.wu
 * @description: TODO
 * @date 2022/3/4
 **/
public class FinalizeObj {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("FinalizeObj本对象准备被回收");
        super.finalize();
    }
}
