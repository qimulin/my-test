package zhou.wu.mytest.reference.soft;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * @author zhou.wu
 * @description: 主要用于监控其回收行为
 * @date 2022/3/4
 **/
public class MySoftReference<T> extends SoftReference<T> {

    private static final int _KB = 300 * 1024;
    private static final int _6MB = 6 * 1024 * 1024;

    private byte[] p1 = new byte[_KB];

    public MySoftReference(T referent) {
        super(referent);
    }

    public MySoftReference(T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("我已执行finalize，可以被垃圾回收了！");
        super.finalize();
    }
}
