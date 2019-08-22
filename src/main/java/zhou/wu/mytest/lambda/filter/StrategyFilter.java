package zhou.wu.mytest.lambda.filter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 策略模式下实现过滤方法
 * Created by lin.xc on 2019/7/11
 */
public class StrategyFilter {

    private List<Product> products = new ArrayList<>();

    @Before
    public void init(){
        products.add(new Product(1L,"苹果手机",8888.88,"手机"));
        products.add(new Product(2L,"华为手机",6666.66,"手机"));
        products.add(new Product(3L,"联想笔记本",7777.77,"笔记本"));
        products.add(new Product(4L,"机械键盘",999.99,"键盘"));
        products.add(new Product(5L,"雷蛇鼠标",222.22,"鼠标"));
    }

    public List<Product> filterProduct(List<Product> products, MyPredicate<Product> predicate){
        List<Product> list = new ArrayList<>();
        for (Product product : products) {
            if (predicate.test(product)) {
                list.add(product);
            }
        }
        return list;
    }

    @Test
    public void test2() throws Exception {
        List<Product> list = filterProduct(products, new FilterProductByName());
        //List<Product> list = filterProduct(products, new FilterProductByPrice());
        for (Product product : list) {
            System.out.println(product);
        }
    }

    @Test
    public void test3() throws Exception {
        List<Product> list = filterProduct(products, new MyPredicate<Product>() {
            @Override
            public boolean test(Product t) {
                return t.getName().contains("手机");
            }
        });
        for (Product product : list) {
            System.out.println(product);
        }
    }
}
