package zhou.wu.mytest.lambda.filter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 1-原始的过滤，每个条件写一个过滤方法
 * Created by lin.xc on 2019/7/11
 */
public class OriginalFilter {
    private List<Product> products = new ArrayList<>();

    @Before
    public void init(){
        products.add(new Product(1L,"苹果手机",8888.88,"手机"));
        products.add(new Product(2L,"华为手机",6666.66,"手机"));
        products.add(new Product(3L,"联想笔记本",7777.77,"笔记本"));
        products.add(new Product(4L,"机械键盘",999.99,"键盘"));
        products.add(new Product(5L,"雷蛇鼠标",222.22,"鼠标"));
    }

    //需求：筛选出所有名称包含手机的商品
    public List<Product> filterProductByName(List<Product> products){
        List<Product> list = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains("手机")) {
                list.add(product);
            }
        }
        return list;
    }
    //需求：筛选出所有价格大于1000的商品
    public List<Product> filterProductByPrice(List<Product> products){
        List<Product> list = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() > 1000) {
                list.add(product);
            }
        }
        return list;
    }

    @Test
    public void test1() throws Exception {
        //List<Product> list = filterProductByName(products);
        List<Product> list = filterProductByPrice(products);
        for (Product product : list) {
            System.out.println(product);
        }
    }

}
