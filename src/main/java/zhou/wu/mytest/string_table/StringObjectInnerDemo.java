package zhou.wu.mytest.string_table;

/**
 * @author zhou.wu
 * @description: 字符串对象比较
 * @date 2022/1/14
 **/
public class StringObjectInnerDemo {
    public static void main(String[] args) {
        String s1 = new String("zhang") + new String(" san");
        String s2 = s1.intern();
        String s3 = "zhang san";
        String s4 = new String("zhang san");
        System.out.println(s1==s2);
        System.out.println(s1==s3);
        System.out.println(s1==s4);
    }
}
