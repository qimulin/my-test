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
/*    public static synchronized LazySingleton getInstance() {
        if( INSTANCE != null ){
            return INSTANCE;
        }
        INSTANCE = new LazySingleton();
        return INSTANCE;
    }*/

    /** 等同于修饰在静态方法上的getInstance方法 */
    public static LazySingleton getInstance() {
        synchronized(LazySingleton.class){  // 这样每次有线程调用一次getInstance，就需要进入一次同步代码块，后续升级为重量级锁后，性能更不佳
            if (INSTANCE != null) {
                return INSTANCE;
            }

            INSTANCE = new LazySingleton();
        }
        return INSTANCE;
    }
}
/**
 * 实际上当INSTANCE只有在被创建以前才需要线程安全保护，创建后就不需要再被线程安全保护了。
 * 鉴于上述这种方式性能不佳，可以参考 {@link zhou.wu.mytest.thread.singleton.DCLLazySingletonP1}
 * */
