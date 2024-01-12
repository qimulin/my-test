package zhou.wu.mytest.static_related;

import static zhou.wu.mytest.static_related.StaticConstant1.*;
import static zhou.wu.mytest.static_related.StaticConstant2.*;

/**
 * @author zhou.wu
 * @date 2023/12/19
 **/
public class ContainStaticFieldObj {

    public static String staticStr = null;

    private String name;

    public ContainStaticFieldObj() {
        // import static 引用的多个常量类，对于重名常量使用，需要指定具体的常量类；不重名则不用
        System.out.println(StaticConstant1.S_1);
        System.out.println(S_2);
        System.out.println(S_3);
    }

    public ContainStaticFieldObj(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaticStr() {
        return staticStr;
    }
}
