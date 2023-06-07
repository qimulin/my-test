package zhou.wu.mytest.mockito;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.verification.VerificationMode;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 为什么要使用 mock？
 * Mock 可以理解为创建一个虚假的对象，或者说模拟出一个对象，在测试环境中用来替换掉真实的对象，以达到我们可以
 * - 验证该对象的某些方法的调用情况，调用了多少次，参数是多少
 * - 给这个对象的行为做一个定义，来指定返回结果或者指定特定的动作
 *
 * @author zhou.wu
 * @description Mockito测试
 * @date 2023/6/7
 **/
class MockitoDemoTest {

    /**
     * 快速mock 的方法，使用@mock 注解。
     * mock 注解需要搭配MockitoAnnotations.openMocks(testClass)方法一起使用
     * Shorthand for mocks creation - @Mock annotation
     * lmportant! This needs to be somewhere in the base class or a test runner:
     * */
    @Mock
    private Random mockRandom;

    /**
     * 测试前准备
     * */
    @BeforeEach
    void setUp(){
        System.out.println("-----测试前准备-----");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMock() {
        /***** Mock对象创建，通常这块代码放在@BeforeEach中 *****/
        // mock方法来自 org.mockito.Mock，它表示可以mock 一个对象或者是接口。
//        Random mockRandom = Mockito.mock(Random.class);
        // 也可以配合mock注解的对象，然后配合MockitoAnnotations.openMocks(testClass)方法一起使用。旧版本叫initMocks
//        MockitoAnnotations.openMocks(this);

        // 输出一个随机数。
        /*
            此处的mockRandom.nextInt()永远返回是0。是因为：当使用mock对象时，如果不对其行为进行定义，则mock对象方法的返回值为返回类型的默认值
            nextInt方法返回值类型为int，所以返回的int的默认值是0
        */
        System.out.println(mockRandom.nextInt());

        /***** Mock对象行为定义 *****/
        // 结合上一句代码，则可以给Mock对象打桩
        // 打桩的意思就是给mock对象规定一行的行为，使其按照我们的要求来执行具体的操作，
        // 示例如下，当执行mockRandom.nextInt方法，则返回100，这样处理的话，调用nextInt(无参)，就会一直返回100
        Mockito.when(mockRandom.nextInt()).thenReturn(100);
        System.out.println(mockRandom.nextInt());

        /***** 验证和断言 *****/
        // 验证是校验待验证的对象是否发生过某些行为，Mockito 中验证的方法是: verify。
        // 验证行为
//        Mockito.verify(mockRandom).nextInt();
        // 验证方法执行的次数
        Mockito.verify(mockRandom, Mockito.times(2)).nextInt();

        // 断言一般是校验行为返回的结果。Mockito断言使用的类是Assertions
        Assertions.assertEquals(100, mockRandom.nextInt());

    }

    @Spy
    private MockitoDemo mockitoDemo;

    /**
     * Spy方法与@Spy注解
     * spy0方法与mock0方法不同的是
     * 1. 被spy的对象会走真实的方法（没有打桩的情况下），而mock对象不会
     * 2. spy0方法的参数是对象实例，mock 的参数是class
     * */
    @Test
    void testSpy() {
        // mockitoDemo是Spy修饰，所以会执行真正的方法，返回结果3；若改为Mock修饰，又不对行为进行打桩，则会返回默认值0
        // 同样，Spy修饰的对象也可以进行打桩，也会取打桩定义的返回
        Assertions.assertEquals(3, mockitoDemo.add(1,2));
    }

    /**
     * 测试结束后
     * */
    @AfterEach
    void after(){
        System.out.println("-----测试结束后-----");
    }
}