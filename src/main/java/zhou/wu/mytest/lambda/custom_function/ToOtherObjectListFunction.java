package zhou.wu.mytest.lambda.custom_function;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lin.xc
 * @date 2021/1/6
 **/
@FunctionalInterface
public interface ToOtherObjectListFunction<T,R> {

    /**
     * 转换List方法
     * */
    default List<R> castList(List<T> paramList, Object... args){
        // 为空判断
        if(CollectionUtils.isEmpty(paramList)){
            return null;
        }
        // 定义返回
        List<R> resultList = new ArrayList<>();
        for(T param: paramList){
            resultList.add(safeCast(param, args));
        }
        return resultList;
    }

    /**
     * 安全转换
     * */
    default R safeCast(T param, Object... args){
        if(param==null){
            return null;
        }
        return cast(param, args);
    }

    /**
     * 转换对象方法，自行传入实现
     * */
    R cast(T param, Object... args);
}
