package zhou.wu.mytest.thread.singleton;

import java.io.Serializable;

/**
 * @author zhou.wu
 * @description: 懒汉单例
 * @date 2022/8/20
 **/
public final class LazySingleton implements Serializable {

    private LazySingleton() {}

    private static LazySingleton INSTANCE = null;

    // 分析这里的线程安全, 并说明有什么缺点
    public static synchronized LazySingleton getInstance() {
        if( INSTANCE != null ){
            return INSTANCE;
        }
        INSTANCE = new LazySingleton();
        return INSTANCE;
    }
}
