package zhou.wu.mytest.thread.synchronize;

/**
 * @author lin.xc
 * @date 2020/11/27
 **/
public class Demo1 {
    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        new Thread(){
            public void run() {
                demo1.syncMethod(Thread.currentThread().getName(), 1);
                System.out.println(Thread.currentThread().getName()+"再次执行！");
                demo1.syncMethod(Thread.currentThread().getName(), 1);
            };
        }.start();

        new Thread(){
            public void run() {
                demo1.syncMethod(Thread.currentThread().getName(), 2);
            };
        }.start();

        new Thread(){
            public void run() {
                demo1.syncMethod(Thread.currentThread().getName(), 1);
            };
        }.start();

        new Thread(){
            public void run() {
                demo1.syncMethod(Thread.currentThread().getName(), 1);
            };
        }.start();
    }

    /**
     * 同步方法
     * */
    public void syncMethod(String threadName, Integer flagId){
        System.out.println("进入方法，参数："+threadName+"，"+flagId);
        synchronized(flagId){
            try {
                System.out.println(threadName+" 执行同步方法需要5s");
                Thread.sleep(5000);
                System.out.println(threadName+" 执行同步方法完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
