package zhou.wu.mytest.reference.weak;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author lin.xc
 * @date 2020/8/7
 **/
public class WeakReferenceTest {
    public static void main(String[] args) {
        ReferenceQueue<String> rq = new ReferenceQueue<>();
        //这里必须用new String构建字符串，而不能直接传入字面常量字符串
        Reference<String> r = new WeakReference<>(new String("java"), rq);
        Reference rf;
        int num=1;
        //一次System.gc()并不一定会回收A，所以要多试几次
        while((rf=rq.poll()) == null) {
            System.out.println("GC-----"+num);
            System.gc();

            num++;
        }
        System.out.println(rf);
        if (rf != null) {
            // 引用指向的对象已经被回收，存入引入队列的是弱引用本身,所以这里最终返回null
            System.out.println(rf.get());
        }
    }
}
