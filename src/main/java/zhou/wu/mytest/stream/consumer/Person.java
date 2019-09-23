package zhou.wu.mytest.stream.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Lin.xc
 * @date 2019/9/23
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Person {
    private Integer age;
    private String name;
}
