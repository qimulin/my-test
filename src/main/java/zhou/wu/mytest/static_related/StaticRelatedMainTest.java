package zhou.wu.mytest.static_related;

/**
 * 对于static修饰的变量的基本使用
 * @author zhou.wu
 * @date 2023/12/19
 **/
public class StaticRelatedMainTest {

    public static void main(String[] args) {
        ContainStaticFieldObj obj1 = new ContainStaticFieldObj("zhangsan");
        // 直接类名+变量名可对其进行更改
        ContainStaticFieldObj.staticStr = "001";
        System.out.println(obj1.getStaticStr());
        ContainStaticFieldObj obj2 = new ContainStaticFieldObj("lisi");
    }
}
