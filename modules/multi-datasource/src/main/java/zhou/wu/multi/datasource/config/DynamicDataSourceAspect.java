package zhou.wu.multi.datasource.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import zhou.wu.multi.datasource.annotion.TargetDataSource;

/**
 * 动态数据源切面类
 * @author lin.xc
 * @date 2021/2/20
 * 用Order保证该AOP在@Transactional之前执行
 **/
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * 目前对于非默认数据源，才需要使用{@link TargetDataSource}注解去修饰选择
     * */
    @Before("within(zhou.wu..*) && @annotation(ano)")
    public void changeDataSource(JoinPoint point, TargetDataSource ano) throws Throwable {
        String choseDsName = ano.value();
        if (!DynamicDataSource.containsDataSource(choseDsName)) {
            throw new RuntimeException(String.format("数据源[%s]不存在！", choseDsName));
        }else {
            logger.debug("选择数据源[{}] > {}", choseDsName, point.getSignature());
            DynamicDataSource.setDataSource(choseDsName);
        }
    }

    @After("@annotation(ano)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ano) {
        logger.debug("清除数据源[{}]选择 > {}", ano.value(), point.getSignature());
        DynamicDataSource.clearDataSource();
    }

}
