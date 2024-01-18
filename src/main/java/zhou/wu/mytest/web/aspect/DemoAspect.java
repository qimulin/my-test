package zhou.wu.mytest.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import zhou.wu.mytest.web.annotation.DemoAnnotation;
import zhou.wu.mytest.web.domain.AutoUserInfo;
import zhou.wu.mytest.web.mapper.dao.AutoUserInfoMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Lin.xc
 * @date 2019/11/6
 */
@Slf4j
//@Aspect
//@Component
public class DemoAspect {

    @Autowired
    private AutoUserInfoMapper autoUserInfoMapper;

    private ExpressionParser parser = new SpelExpressionParser();

    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(zhou.wu.mytest.web.annotation.DemoAnnotation)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        // 获取方法
//        Method method = getMethod(joinPoint);
        DemoAnnotation annotation = method.getAnnotation(DemoAnnotation.class);
//            Integer primaryId = parseKey(annotation.primaryId(), joinPoint);
        //获取缓存key的表达式,并根据上下文等数据计算表达式
        Object[] arguments = joinPoint.getArgs();
        Integer age = parseSpel(method, arguments, annotation.primaryId(), Integer.class, 0);
        System.out.print("打印：" + annotation.value() + " 开始前");
    }

    @Around("annotationPointCut()")
    public Object advice(ProceedingJoinPoint joinPoint) {
        System.out.println("通知之开始");
        Object retmsg = null;
        try {
            // 获取方法
            Method method = getMethod(joinPoint);
            DemoAnnotation annotation = method.getAnnotation(DemoAnnotation.class);
//            Integer primaryId = parseKey(annotation.primaryId(), joinPoint);
            //获取缓存key的表达式,并根据上下文等数据计算表达式
            Object[] arguments = joinPoint.getArgs();
            Integer age = parseSpel(method, arguments, annotation.primaryId(), Integer.class, 0);
            System.out.println("age="+age);
            Integer age2 = parseKey(annotation.primaryId(), joinPoint);
            System.out.println("age2="+age2);
            retmsg = joinPoint.proceed();
            System.out.println("++++++++" + retmsg);
            AutoUserInfo autoUserInfo = autoUserInfoMapper.selectByPrimaryKey(1);
            System.out.println(autoUserInfo.getUsername());
            Field[] fields = retmsg.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if ("userage".equals(field.getName())) {
                    field.set(retmsg, null);
                    field.setAccessible(false);
                    continue;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("通知之结束");
        return retmsg;
    }

    @After("annotationPointCut()")
    public void after() {
        System.out.println("after方法执行后");
    }


    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint
                        .getTarget()
                        .getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(),
                                method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;

    }

    /**
     * 解析 spel 表达式
     *
     * @param method    方法
     * @param arguments 参数
     * @param spel      表达式
     * @param clazz     返回结果的类型
     * @param defaultResult 默认结果
     * @return 执行spel表达式后的结果
     */
    private <T> T parseSpel(Method method, Object[] arguments, String spel, Class<T> clazz, T defaultResult) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], arguments[len]);
        }
        try {
            Expression expression = parser.parseExpression(spel);
            return expression.getValue(context, clazz);
        } catch (Exception e) {
            return defaultResult;
        }
    }

    /**
     * 计算spel表达式
     *
     * @param expression 表达式
     * @param context    上下文
     * @return String的缓存key
     */
    private Integer parseKey(String expression, JoinPoint context) {
        //获取切入点的方法信息
        MethodSignature methodSignature = (MethodSignature) context.getSignature();
        Method method = methodSignature.getMethod();

        // 获取传入参数值
        Object[] args = context.getArgs();
        if (args == null || args.length == 0) {
            // 无参传入,直接计算表达式(无需参数上下文)
            return parser.parseExpression(expression).getValue(Integer.class);
        }

        // 获取参数名
        String[] parameterNames = discoverer.getParameterNames(method);
        if (parameterNames.length > args.length) {
            //由于java不允许有匿名参数,所以如果参数名多于参数值,则必为非法
            log.error("参数值的长度少于参数名长度, 方法:{}, 参数名长度: {},参数值长度:{}", method, parameterNames.length, args.length);
            throw new IllegalArgumentException("参数传入不足");
        }

        // 将参数名与参数值放入参数上下文
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            evaluationContext.setVariable(parameterNames[i], args[i]);
        }

        // 计算表达式(根据参数上下文)
        return parser.parseExpression(expression).getValue(evaluationContext, Integer.class);
    }

}