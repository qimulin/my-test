package zhou.wu.mytest.test;

/**
 * @author Lin.xc
 * @date 2019/9/25
 */
public class Test1 {
    public static void main(String[] args) {
        try{
            String str = "2.x";
            Integer.valueOf(str);
        }catch (Exception e){
            System.out.println("catch");
            throw e;
        }finally {
            System.out.println("finally");
        }
    }
}
