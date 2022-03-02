package zhou.wu.mytest;

/**
 * @author zhou.wu
 * @description: 感受StringTable
 * @date 2022/1/10
 **/
public class StringTableDemo {

    /**
     * 打印结果：
     * true
     * false
     * */
    public static void main(String[] args) {
        String s = new String("a") + new String("b");
        // 将这个字符串对象尝试放入串池，如果串池中已有，则不会放入，并且会把串池中的对象返回
        String s1 = s.intern(); // 这里s对象入不了串池，但会把串池的”ab“对象x赋给s1
        String x = "ab";
        System.out.println(s1==x);
        System.out.println(s==x);
    }
}
