package zhou.wu.mytest.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhou.wu
 * @date 2023/9/15
 **/
@Slf4j
public class TryCatchTest {

    public static void main(String[] args) {
        // 使用try-catch 无论是哪种情况，方法最终都需要有返回或者异常
        returnBeforeFinally();
        returnInFinally();
        returnAfterFinally();
    }

    /**
     * 结果：
     * 2
     * 3
     * */
    public static int returnBeforeFinally(){
        log.info("----- returnBeforeFinally -----");
        try {
            int i = 1/0;
            System.out.println("1");
            return 3;
        } catch (Exception e){
            // 先到异常获取，再到finally
            System.out.println("2");
//            throw new RuntimeException(e);
            return 3;
        }finally {
            System.out.println("3");
        }
    }

    /**
     * 结果：
     * 2
     * 3
     * */
    public static int returnInFinally(){
        log.info("----- returnInFinally -----");
        try {
            int i = 1/0;
            System.out.println("1");
        } catch (Exception e){
            System.out.println("2");
//            throw new RuntimeException(e);
        } finally {
            System.out.println("3");
            return 3;
        }
    }

    /**
     * 结果：
     * 2
     * 3
     * 4
     * */
    public static int returnAfterFinally(){
        log.info("----- returnAfterFinally -----");
        try {
            int i = 1/0;
            System.out.println("1");
        } catch (Exception e){
            System.out.println("2");
//            throw new RuntimeException(e);
        }finally {
            System.out.println("3");
        }
        System.out.println("4");
        return 3;
    }
}
