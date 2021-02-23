package zhou.wu.multi.datasource.annotion;

import zhou.wu.multi.datasource.config.DynamicDataSource;

import java.lang.annotation.*;

/**
 * @author lin.xc
 * @date 2021/2/20
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    /**
     * 留个name属性，看后面是否需要支持多目标数据源
     * */
    String name() default DynamicDataSource.KEY_TARGET;;
}
