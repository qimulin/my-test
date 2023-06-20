package zhou.wu.mytest.reference.soft;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * 演示软引用
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class SoftDemo3 {

    private static final int _1MB = 1024 * 1024;

    private static final int _4MB = 4 * 1024 * 1024;

    public static void main(String[] args) throws IOException {
        soft();
        System.out.println("main 方法结束！");
    }

    public static void soft(){
        ReferenceQueue<byte[]> rQueue = new ReferenceQueue<>();
        new Thread(()->{
            while (true){
                Reference<? extends byte[]> poll = rQueue.poll();
                if(poll!=null){
                    System.out.println("队列里拿引用对象："+poll);
                    try {
                        // 移除这个队列元素
                        rQueue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }).start();

        MySoftReference<byte[]> ref = new MySoftReference<>(new byte[1024], rQueue);
//        System.in.read();
        int count = 1;
        while(true){
            try {
                Thread.sleep(10000);
                System.out.println("sleep 10s "+count);
                if(count==10){
                    ref = null;
                    return;
                }
                System.gc();
                count++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
