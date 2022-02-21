package zhou.wu.mytest.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lin.xc on 2019/7/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestObj {
    private Long id;
    private String name;

    public void getMsg(){
        System.out.println("getMsg");
    }
}
