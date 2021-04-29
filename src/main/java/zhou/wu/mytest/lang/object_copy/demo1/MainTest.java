package zhou.wu.mytest.lang.object_copy.demo1;

/**
 * @author lin.xc
 * @date 2021/4/28
 **/
public class MainTest {
    public static void main(String[] args) {
        System.out.println("---------- 浅拷贝 ----------");
        Person p = new Person(23, "zhang");
        Person p1 = p;
        // p和p1指向的是同一个对象，p和p1只是引用而已。它们都指向了一个相同的对象Person(23, "zhang") 。 可以把这种现象叫做引用的复制
        System.out.println(p);
        System.out.println(p1);

        System.out.println("---------- 深拷贝 ----------");
        PersonCp pcp = new PersonCp(24, "li");
        PersonCp pcp_new = new PersonCp();
        PersonCp pcp1 = null;
        try {
            // clone方法比new好的地方就是属性也可以都过来
            pcp1 = (PersonCp) pcp.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        // p1指向了新的对象，而不是把原来的对象的地址赋给p1
        System.out.println(pcp_new);
        System.out.println(pcp);
        System.out.println(pcp1);
    }
}
