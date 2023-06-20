package zhou.wu.mytest.reference.soft;

import java.io.IOException;

/**
 * 演示软引用
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class SoftDemo2 {

    private static final int _1MB = 1024 * 1024;
    private static final int _4MB = 4 * 1024 * 1024;

    public static void main(String[] args) throws IOException {
        MySoftReference<byte[]> ref = new MySoftReference<>(new byte[_4MB]);
        int count = 1;
        while(true){
            try {
                Thread.sleep(10000);
                System.out.println("sleep 10s "+count);
//                if(count==5){
//                    ref = null;
//                }
                System.gc();
                count++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
