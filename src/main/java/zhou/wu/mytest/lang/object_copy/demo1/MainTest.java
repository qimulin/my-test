package zhou.wu.mytest.lang.object_copy.demo1;

/**
 * @author lin.xc
 * @date 2021/4/28
 **/
public class MainTest {
    public static void main(String[] args) {
        System.out.println("---------- 浅拷贝 ----------");
        Person p = new Person(23, "张三");
        Person p1 = p;
        // p和p1指向的是同一个对象，p和p1只是引用而已。它们都指向了一个相同的对象Person(23, "zhang") 。 可以把这种现象叫做引用的复制
        System.out.println(p);
        System.out.println(p1);

        System.out.println("---------- 深拷贝 ----------");
        PersonCp pcp = new PersonCp(24, "李四");
        Address address = new Address();
        address.setCity("杭州");
        address.setCounty("西湖区");
        address.setStreet("东部软件园");
        pcp.setAddress(address);
        PersonCp pcp_new = new PersonCp();
        PersonCp pcp_clone = null;
        try {
            // clone方法比new好的地方就是属性也可以都过来。然后克隆体就算设置自己的属性值，也不会影响原始本体
            pcp_clone = pcp.clone();
            pcp_clone.setAge(18);
            pcp_clone.setName("王五");
            Address address1 = pcp_clone.getAddress();
            // 这里修改会影响本体的值，若不想影响本体，则里面引用类型的Address也需要支持Clone，且PersonCp克隆方法里需要调用Address的clone方法
            address1.setStreet("北部软件园");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        // p1指向了新的对象，而不是把原来的对象的地址赋给p1
        System.out.println(pcp_new);
        System.out.println(pcp);
        System.out.println(pcp_clone);
    }
}
