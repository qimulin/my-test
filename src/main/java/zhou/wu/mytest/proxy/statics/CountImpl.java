package zhou.wu.mytest.proxy.statics;

import zhou.wu.mytest.proxy.intf.Count;

/**
 * 委托类(包含业务逻辑)
 * @author Lin.xc
 * @date 2020/5/12
 */
public class CountImpl  implements Count {

    @Override
    public void queryCount() {
        System.out.println("查看账户...");

    }

    @Override
    public void updateCount() {
        System.out.println("修改账户...");

    }
}
