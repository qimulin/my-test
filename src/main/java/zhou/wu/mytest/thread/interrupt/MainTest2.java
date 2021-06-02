package zhou.wu.mytest.thread.interrupt;

/**
 * @author lin.xc
 * @date 2021/6/2
 **/
public class MainTest2 {
    public static void main(String[] args) {
        try {
            MyThread2 thread = new MyThread2();
            thread.start();
            Thread.sleep(20); // modify 2000 to 20
            // 请求中断MyThread线程
            System.out.println("wait");
            thread.sleep(3000);
            System.out.println("wait后到这里");
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end!");
    }

}
