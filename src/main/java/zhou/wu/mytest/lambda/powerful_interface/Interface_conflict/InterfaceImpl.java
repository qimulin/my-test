package zhou.wu.mytest.lambda.powerful_interface.Interface_conflict;

/**
 * Created by lin.xc on 2019/7/19
 * 接口本来就支持多实现
 */
public class InterfaceImpl implements InterfaceA, InterfaceB {

    /**
     * 接口A和B中都有method方法，有冲突，必须自己覆盖该方法
     * 当同时实现的多个接口中存在签名一样的若干个方法体实现（无论内容是否完全相同）时，实现类必须进行覆盖重写以解决冲突。
     * 而且并不能指定选择哪个接口中的实现。
     * */
    @Override
    public void method() {
        System.out.println("实现方案C");
    }

    @Override
    public void absMethod() {
        System.out.println("实现抽象方案C");
    }

}
