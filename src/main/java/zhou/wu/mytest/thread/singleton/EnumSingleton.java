package zhou.wu.mytest.thread.singleton;

/**
 * @author zhou.wu
 * @description: 枚举单例
 * @date 2022/8/20
 **/
// 问题1：枚举单例是如何限制实例个数的
// 问题2：枚举单例在创建时是否有并发问题
// 问题3：枚举单例能否被反射破坏单例
// 问题4：枚举单例能否被反序列化破坏单例
// 问题5：枚举单例属于懒汉式还是饿汉式
// 问题6：枚举单例如果希望加入一些单例创建时的初始化逻辑该如何做
public enum EnumSingleton {
    INSTANCE;
}
