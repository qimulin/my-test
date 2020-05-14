package zhou.wu.mytest.proxy.dynamic.jdk;

import zhou.wu.mytest.proxy.intf.BookFacade;

/**
 * @author Lin.xc
 * @date 2020/5/12
 */
public class BookFacadeImpl implements BookFacade {
    @Override
    public void addBook() {
        System.out.println("增加图书方法。。。");
    }
}
