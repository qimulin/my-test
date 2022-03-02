package zhou.wu.mytest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lin.xc
 * @date 2021/3/25
 **/
@Slf4j
public class MainTest {
    public static void main(String[] args) {
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            if ("2".equals(temp)) {
                a.remove(temp);
            }
        }
    }

}

@Data
class Demo{
    private String str;
}
