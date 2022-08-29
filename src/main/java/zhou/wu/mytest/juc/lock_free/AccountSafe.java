package zhou.wu.mytest.juc.lock_free;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhou.wu
 * @description: 账户接口线程安全的实现
 * @date 2022/8/29
 **/
public class AccountSafe implements Account {

    private AtomicInteger balance;    // 共享资源

    /** 构造传入余额 */
    public AccountSafe(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        // 麻烦的一种写法（能体验AtomicInteger内部的原理）
        while (true) {
            // 获取余额的最新值
            int prev = balance.get();
            // 余额减去取款金额=剩余余额（=要修改的余额），这个值还是在工作内存上，没有存到主存中去
            int next = prev - amount;
            // compareAndSet（类似乐观锁的实现）: 参数1-当前余额最新值；参数2-要修改的值
            if (balance.compareAndSet(prev, next)) {
                // 当参数1的值等于balance当前的值，则返回true；反之则返回false，就会在while循环做下一次尝试
                break;
            }
        }
        // 可以简化为下面的方法
        // balance.addAndGet(-1 * amount);
    }
}
