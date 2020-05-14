package zhou.wu.mytest.proxy.dynamic.cglib;

/**
 * @author Lin.xc
 * @date 2020/5/12
 */
public class DynamicProxyCglibMain {
    public static void main(String[] args) {
        BookFacadeImpl1 bookFacade=new BookFacadeImpl1();
        BookFacadeCglib  cglib=new BookFacadeCglib();
        BookFacadeImpl1 bookCglib=(BookFacadeImpl1)cglib.getInstance(bookFacade);
        bookCglib.addBook();
        bookCglib.lendBook();
    }
}
