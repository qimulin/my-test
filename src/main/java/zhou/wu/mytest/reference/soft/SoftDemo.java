package zhou.wu.mytest.reference.soft;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 演示软引用
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class SoftDemo {

    private static final int _4MB = 4 * 1024 * 1024;


    public static void main(String[] args) throws IOException {
        /*List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new byte[_4MB]);
        }

        System.in.read();*/
        soft();


    }

    public static void soft() {
        // list --> SoftReference --> byte[]

        List<SoftReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB]);
            System.out.println(ref.get());
            list.add(ref);
            System.out.println(list.size());

        }
        int size = list.size();
        System.out.println("循环结束：" + size);
        for (int i=0; i<size; i++) {
            SoftReference<byte[]> ref = list.get(i);
            System.out.println(i+":"+ref.get());
        }
    }

}
