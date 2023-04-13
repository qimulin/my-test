package zhou.wu.boot.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhou.wu
 * @description 用户对象
 * @date 2023/4/13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;
}
