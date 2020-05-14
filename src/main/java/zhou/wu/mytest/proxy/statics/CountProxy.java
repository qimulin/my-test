package zhou.wu.mytest.proxy.statics;

import zhou.wu.mytest.proxy.intf.Count;

/**
 * @author Lin.xc
 * @date 2020/5/12
 */
public class CountProxy  implements Count {

    private CountImpl countImpl;  //组合一个业务实现类对象来进行真正的业务方法的调用

    /**
     * 覆盖默认构造器
     *
     * @param countImpl
     */
    public CountProxy(CountImpl countImpl) {
        this.countImpl = countImpl;
    }

    @Override
    public void queryCount() {
        System.out.println("查询账户的预处理——————");
        // 调用真正的查询账户方法
        countImpl.queryCount();
        System.out.println("查询账户之后————————");
    }

    @Override
    public void updateCount() {
        System.out.println("修改账户之前的预处理——————");
        // 调用真正的修改账户操作
        countImpl.updateCount();
        System.out.println("修改账户之后——————————");

    }
}
