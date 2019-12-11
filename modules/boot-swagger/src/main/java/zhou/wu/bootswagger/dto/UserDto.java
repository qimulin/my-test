package zhou.wu.bootswagger.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Lin.xc
 * @date 2019/10/28
 */
@Data
@ApiModel("用户DTO")
public class UserDto {
    @NotBlank(message = "用户名必须输入")
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @NotBlank (message = "手机号必须输入")
    @ApiModelProperty (value = "手机号", required = true)
    private String mobilePhone;
}
