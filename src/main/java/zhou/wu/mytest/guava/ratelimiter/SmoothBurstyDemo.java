package zhou.wu.mytest.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Guava的RateLimiter提供了令牌桶算法实现：平滑突发限流(SmoothBursty)和平滑预热限流(SmoothWarmingUp)实现。
 * burst 美[bɜːrst] 突发
 * @author zhou.wu
 * @date 2024/2/6
 **/
public class SmoothBurstyDemo {

    public static void main(String[] args) {
//        testSmoothBursty();
//        testSmoothBursty2();
        testSmoothBursty3();
    }

    /**
     * 使用RateLimiter的静态方法创建一个限流器，设置每秒放置的令牌数为5个。返回的RateLimiter对象可以保证1秒内不会给超过5个令牌，并且以固定速率进行放置，达到平滑输出的效果。
     * */
    public static void testSmoothBursty() {
        RateLimiter r = RateLimiter.create(5);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire() + "s");
        }
        /**
         * output: 基本上都是0.2s执行一次，符合一秒发放5个令牌的设定。
         * get 1 tokens: 0.0s
         * get 1 tokens: 0.182014s
         * get 1 tokens: 0.188464s
         * get 1 tokens: 0.198072s
         * get 1 tokens: 0.196048s
         * get 1 tokens: 0.197538s
         * get 1 tokens: 0.196049s
         */
    }

    /**
     * RateLimiter使用令牌桶算法，会进行令牌的累积，如果获取令牌的频率比较低，则不会导致等待，直接获取令牌。
     * */
    public static void testSmoothBursty2() {
        RateLimiter r = RateLimiter.create(2);
        while (true)
        {
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            // 由于有累积，所以睡眠2s后下面这三个获取基本都是很快速的
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
            /**
             * output:
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * end
             * get 1 tokens: 0.499796s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             */
        }
    }

    /**
     * RateLimiter由于会累积令牌，所以可以应对突发流量。在下面代码中，有一个请求会直接请求5个令牌，但是由于此时令牌桶中有累积的令牌，足以快速响应。
     * RateLimiter在没有足够令牌发放时，采用滞后处理的方式，也就是前一个请求获取令牌所需等待的时间由下一次请求来承受，也就是代替前一个请求进行等待。（自解：那得好好看看这个时间是怎么计算的了）
     * */
    public static void testSmoothBursty3() {
        RateLimiter r = RateLimiter.create(5);
        while (true)
        {
            System.out.println("get 5 tokens: " + r.acquire(5) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
            /**
             * output:
             * get 5 tokens: 0.0s
             * get 1 tokens: 0.996766s 滞后效应，需要替前一个请求进行等待。前1s的5个令牌被用掉了，这里不能马上获取到令牌，必须等待将近1s
             * get 1 tokens: 0.194007s
             * get 1 tokens: 0.196267s
             * end
             * get 5 tokens: 0.195756s
             * get 1 tokens: 0.995625s 滞后效应，需要替前一个请求进行等待
             * get 1 tokens: 0.194603s
             * get 1 tokens: 0.196866s
             */
        }
    }
}
