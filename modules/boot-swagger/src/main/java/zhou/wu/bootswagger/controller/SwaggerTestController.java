package zhou.wu.bootswagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zhou.wu.bootswagger.dto.UserDto;

/**
 * @author Lin.xc
 * @date 2019/10/28
 */
@Slf4j
@RestController
@RequestMapping("/swagger-test")
@Api(value = "Swagger尝试", tags = "Swagger测试")
public class SwaggerTestController {

    @ResponseBody
    @PostMapping(value = "/user")
    @ApiOperation(value = "添加参数")
    private UserDto addUser(
            @RequestBody
            @Validated UserDto userDto
    ){
        log.info("User=[{}]",userDto);
        return userDto;
    }

    @ResponseBody
    @GetMapping(value = "/param")
    @ApiOperation(value = "获取参数")
    private String getParam(
            @ApiParam(value = "参数", required = true)
            @RequestParam(value = "param") String param
    ){
        log.info("param=[{}]",param);
        return param;
    }
}
