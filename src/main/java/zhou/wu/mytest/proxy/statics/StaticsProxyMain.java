package zhou.wu.mytest.proxy.statics;

/**
 * @author Lin.xc
 * @date 2020/5/12
 */
public class StaticsProxyMain {
    public static void main(String[] args) {
        CountImpl countImpl = new CountImpl();
        CountProxy countProxy = new CountProxy(countImpl);
        countProxy.updateCount();
        countProxy.queryCount();
    }
}
