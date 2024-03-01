package zhou.wu.boot.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhou.wu.boot.web.utils.I18nMessageUtils;

/**
 * @author zhou.wu
 * @date 2024/2/29
 **/
@Slf4j
@RestController
@RequestMapping("/i18n-test")
public class I18nTestController {

    /**
     * 简单示例
     *
     * @author zhou.wu
     * @date 2024/2/29
     */
    @GetMapping("/hello")
    public String hello(String key){
        return I18nMessageUtils.getMessage(key);
    }

    /**
     * 带参示例
     *
     * @author zhou.wu
     * @date 2024/2/29
     */
    @GetMapping("/with-param")
    public String withParam(String str){
        String key = "demo.illegal.param";
        return I18nMessageUtils.getMessage(key, new String[]{str});
    }
}
