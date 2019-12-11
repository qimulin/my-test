package zhou.wu.mytest.web.annotation;

import java.lang.annotation.*;

/**
 * @author Lin.xc
 * @date 2019/11/6
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DemoAnnotation {
    String value();
    String primaryId() default "";
}
