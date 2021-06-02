package zhou.wu.mytest.thread.interrupt;

/**
 * @author lin.xc
 * @date 2021/6/2
 **/
public class MyThread2 extends Thread {

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 50000; i++) {
            if (i==10000) {
                System.out.println("thread inner interrupt");
               currentThread().interrupt();
            }
            System.out.println("i=" + (i + 1));
        }
        System.out.println("this line cannot be executed. cause thread throws exception"); //这行语句不会被执行!!!
    }
}
