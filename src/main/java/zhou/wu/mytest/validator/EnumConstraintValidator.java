package zhou.wu.mytest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * 自定义枚举验证器
 * @author Lin.xc
 * @date 2019/11/15
 */
public class EnumConstraintValidator implements ConstraintValidator<EnumConstraint, Object> {
    /**
     * 枚举类
     */
    Class<?>[] clazz;
    /**
     * 指定校验字段
     */
    String assignFieldName;
    /**
     * 允许为空
     */
    boolean allowEmpty;

    @Override
    public void initialize(EnumConstraint constraintAnnotation) {
        clazz = constraintAnnotation.target();
        assignFieldName = constraintAnnotation.assignFieldName();
        allowEmpty = constraintAnnotation.allowEmpty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (clazz.length > 0) {
            if(null==value && allowEmpty){
                // 允许空值，且值为空值，则放过
                return true;
            }
            try {
                for (Class<?> clz : clazz) {
                    if (clz.isEnum()) {
                        // 获取枚举勒种每个枚举常量对象
                        Object[] objs = clz.getEnumConstants();
                        if(objs.length>0){
                            // 只需要取一个枚举常量对象就可以判断了
                            Object obj = objs[0];
                            Class<?> enumClz = obj.getClass();
                            Field[] enumFields = enumClz.getDeclaredFields();
                            // 遍历枚举中每个枚举对象
                            for (Field enumField : enumFields) {
                                // 只遍历枚举类型字段
                                if(clz.equals(enumField.getType())){
                                    enumField.setAccessible(true);
                                    Object enumValue = enumField.get(obj);
                                    // 获取指定校验的字段对象
                                    Field assignField = enumValue.getClass().getDeclaredField(assignFieldName);
                                    assignField.setAccessible(true);
                                    if(assignField.get(enumValue).equals(value)){
                                        return true;
                                    }
                                }else{
                                    continue;
                                }
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        } else {
            return true;
        }
        return false;
    }
}
