package zhou.wu.mytest.test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Lin.xc
 * @date 2019/11/14
 */
public class RegexMatches {
    public static void main(String args[]) {
//        String str = "林喜纯·林喜纯林喜纯林喜纯林12";
//        String pattern = "[·\u4e00-\u9fa5]{1,15}";
//        String str = "12334sasasa";
//        String pattern = "[\\d]{1,11}"; // "[\\d]{1,11}";
//        /w
        String str = "12345";
        String pattern = "^[\\w]{6,16}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }

}
