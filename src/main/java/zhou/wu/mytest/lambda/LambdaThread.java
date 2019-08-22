package zhou.wu.mytest.lambda;

/**
 * Created by lin.xc on 2019/7/11
 */
public class LambdaThread {
    public static void main(String[] args) {
        // 非lambda新建线程并启动
        new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("Hello World - 1!");
            }
        }).start();
        // lambda新建线程并启动
        new Thread(
                () -> System.out.println("Hello world - 2!")
        ).start();

        Runnable r = ()-> System.out.println("Hello");
        // 这种表达仅支持接口
//        TestIntf intf = ()-> System.out.println("111");
    }
}
