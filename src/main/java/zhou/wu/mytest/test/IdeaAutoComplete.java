package zhou.wu.mytest.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Idea自动补全
 * @author Lin.xc
 * @date 2019/9/25
 */
public class IdeaAutoComplete {
    public static void main(String[] args) {
        String str = "Lin.xc";
        List<String> strList = new ArrayList<>();
        boolean flag =true;
        // .var
        String s = "Lin.xc";
        // .null
        if (str == null) {

        }
        // .nn 等同于 .notnull
        if (str != null) {

        }
        // .for
        for (String s1 : strList) {

        }
        // .fori
        for (int i = 0; i < strList.size(); i++) {

        }
        // .not取反
        if(!flag){

        }
        // .cast强转
        Object num = 1;
        System.out.println(((Integer) num));
        // .return 返回值
//        return str;
    }
}
