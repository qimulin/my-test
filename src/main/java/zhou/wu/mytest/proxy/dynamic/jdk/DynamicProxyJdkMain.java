package zhou.wu.mytest.proxy.dynamic.jdk;

import zhou.wu.mytest.proxy.intf.BookFacade;

/**
 * @author Lin.xc
 * @date 2020/5/12
 */
public class DynamicProxyJdkMain {
    public static void main(String[] args) {
        /**
         * 在使用时，首先创建一个业务实现类对象和一个代理类对象，然后定义接口引用（这里使用向上转型）
         * 并用代理对象.bind(业务实现类对象)的返回值进行赋值。最后通过接口引用调用业务方法即可。
         * （接口引用真正指向的是一个绑定了业务类的代理类对象，所以通过接口方法名调用的是被代理的方法们）
         * */
        BookFacadeImpl bookFacadeImpl=new BookFacadeImpl();
        BookFacadeProxy proxy = new BookFacadeProxy();
        BookFacade bookfacade = (BookFacade) proxy.bind(bookFacadeImpl);
        bookfacade.addBook();
    }
}
