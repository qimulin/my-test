package zhou.wu.mytest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举校验注解
 * @author Lin.xc
 * @date 2019/11/15
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumConstraintValidator.class})
@Documented
public @interface EnumConstraint {

    /**
     * 提示消息
     */
    String message() default "限定枚举值校验不通过";

    /**
     * 对应的枚举类
     */
    Class<?>[] target() default {};

    /**
     * 指定校验枚举字段名
     */
    String assignFieldName() default "value";

    /**
     * 允许空值
     * */
    boolean allowEmpty() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
