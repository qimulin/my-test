package zhou.wu.mytest.collection.set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lin.xc
 * @date 2020/8/26
 **/
public class SetTest {
    public static void main(String[] args) {
        List<String> nameList = new ArrayList<>();
        nameList.add("曹操");
        nameList.add("刘备");
        nameList.add("孙权");
        nameList.add("曹操");
        nameList.add("孙权");
        nameList.add("曹操");

        Set<String> nameSet = new HashSet<>();
        for (String inxStr : nameList) {
            if (nameSet.contains(inxStr)) {
                // 如果别名集合中包含，则需要给该字段别名重命名
                int i = 0;
                // 循环去校验名字是否重复
                while (true) {
                    String columnAliasName =  inxStr+ "_" + i ;
                    System.out.println("重名字段重命名columnAliasName："+columnAliasName);
                    if (!nameSet.contains(columnAliasName)) {
                        inxStr = columnAliasName;
                        System.out.println("重命名："+columnAliasName);
                        break;
                    } else {
                        i++;
                    }
                }
                System.out.println("修改重命名");
            }
            nameSet.add(inxStr);
        }
        System.out.println("结束！");
    }
}
