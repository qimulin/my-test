package zhou.wu.mytest.reference.phantom;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou.wu
 * @description: 虚引用示例
 * @date 2022/2/16
 **/
public class PhantomDemo {

    private static final int _4MB = 4*1024*1024;

    private static ReferenceQueue<M> QUEUE = new ReferenceQueue<>();
    public static final List<byte[]> LIST = new ArrayList<>();

    public static void main(String[] args) {

        PhantomReference<M> phantomReference = new PhantomReference<>(new M(), QUEUE);

        new Thread(()->{
            while (true){
                LIST.add(new byte[1024*1024]);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(()->{
            while (true){
                Reference<? extends M> poll = QUEUE.poll();
                if(poll!=null){
                    System.out.println("队列里拿引用对象："+poll);
                    break;
                }
            }
        }).start();
//        M m = new M();
//        System.out.println("Demo对象："+m);
//        PhantomReference<M> r = new PhantomReference<>(m, rq);
//        System.out.println("Demo对象："+m);
//        System.out.println("虚引用引用的对象："+r.get()); // 永远为null（幽灵引用相当于无引用）
//        for (int i = 0; i < 10; i++) {
//            PhantomReference<M> n = new PhantomReference<>(new M(), rq);
//        }
//
//        while (true){
//            try {
//                System.gc();
//                Thread.sleep(60000);
//                System.out.println("时间流逝一分钟");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        List<M> spaceList = new ArrayList<>();
//        while (true){
//            // 一直创建对象
//            M wm = new M();
//            spaceList.add(wm);
//            System.out.println("spaceList Size = "+spaceList.size());
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//
//            }
//        }
    }
}

class M{
    private byte[] p = new byte[1024*10];

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this+"被回收！");
        super.finalize();
    }
}
