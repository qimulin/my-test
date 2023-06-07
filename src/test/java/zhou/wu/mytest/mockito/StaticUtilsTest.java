package zhou.wu.mytest.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;

/**
 * @author zhou.wu
 * @description 静态方法Mock测试
 * @date 2023/6/7
 **/
class StaticUtilsTest {

    @Test
    void range() {
        MockedStatic<StaticUtils> staticDemo = Mockito.mockStatic(StaticUtils.class);
        // 行为定义
        staticDemo.when(()->StaticUtils.range(2, 6)).thenReturn(Arrays.asList(10, 11, 12));
        Assertions.assertTrue(StaticUtils.range(2, 6).contains(10));
    }

    @Test
    void name() {
        MockedStatic<StaticUtils> staticDemo = Mockito.mockStatic(StaticUtils.class);
        staticDemo.when(StaticUtils::name).thenReturn("Print");
        Assertions.assertEquals("Print", StaticUtils.name());
    }

//    @Test
//    void rangeAfterClose() {
//        try(MockedStatic<StaticUtils> staticDemo = Mockito.mockStatic(StaticUtils.class)){
//            // 行为定义
//            staticDemo.when(()->StaticUtils.range(2, 6)).thenReturn(Arrays.asList(10, 11, 12));
//            Assertions.assertTrue(StaticUtils.range(2, 6).contains(10));
//        }
//    }
//
//    @Test
//    void nameAfterClose() {
//        try(MockedStatic<StaticUtils> staticDemo = Mockito.mockStatic(StaticUtils.class)){
//            staticDemo.when(StaticUtils::name).thenReturn("Print");
//            Assertions.assertEquals("Print", StaticUtils.name());
//        }
//    }
}
/**
 * 后记：
 * 假如上面的方法单独执行的断言都成功，转而直接运行这个测试类的话，却会报错：
 * org.mockito.exceptions.base.MockitoException:
 * For zhou.wu.mytest.mockito.StaticUtils, static mocking is already registered in the current thread
 *
 * To create a new mock, the existing static mock registration must be deregistered
 *
 * static mocking注册在当前线程中，如果要创建新的mock，则需要将注册的注销
 * 原因：内部用了ThreadLocal去开启mock的
 * 如何注销？
 * 1、由于MockedStatic实现了AutoCloseable的close方法，则可以用jdk1.7新增的try-catch-resources语法在try的()内部创建资源，创建的资源在退出try-block时候会自动调用该资源的close方法。
 * 2、或者其实可以提出为静态成员变量，例如： static MockedStatic<StaticUtils> staticDemo = Mockito.mockStatic(StaticUtils.class);
 * */