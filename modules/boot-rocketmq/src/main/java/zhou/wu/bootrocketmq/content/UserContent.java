package zhou.wu.bootrocketmq.content;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Function 发送消息体
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@Getter
@Setter
public class UserContent {
    private String username;
    private String pwd;
}
