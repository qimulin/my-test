package zhou.wu.mytest.thread.singleton;

import java.io.Serializable;

/**
 * @author zhou.wu
 * @description: 饿汉单例
 * @date 2022/8/20
 **/
// 问题1：为什么加 final
// 问题2：如果实现了序列化接口, 还要做什么来防止反序列化破坏单例
public final class HungrySingleton implements Serializable {

    // 问题3：为什么设置为私有? 是否能防止反射创建新的实例?
    private HungrySingleton() {}

    // 问题4：这样初始化是否能保证单例对象创建时的线程安全?
    private static final HungrySingleton INSTANCE = new HungrySingleton();

    // 问题5：为什么提供静态方法而不是直接将 INSTANCE 设置为 public, 说出你知道的理由
    public static HungrySingleton getInstance() {
        return INSTANCE;
    }

    public Object readResolve() {
        return INSTANCE;
    }
}
