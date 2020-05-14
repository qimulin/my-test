package zhou.wu.mytest.proxy.dynamic.cglib;

/**
 * @author Lin.xc
 * @date 2020/5/12
 */
public class BookFacadeImpl1 {
    public void addBook() {
        System.out.println("新增图书...");
    }

    public void lendBook() {
        System.out.println("借出图书...");
    }
}
