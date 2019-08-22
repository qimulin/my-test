package zhou.wu.mytest.lambda.filter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Lambda表达式实现过滤方法
 * Created by lin.xc on 2019/7/15
 */
public class LambdaFilter {

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

    /**
     * 使用Lambda表达式优化
     * Lambda表达式可以理解为是一个匿名函数，也就是一段可以传递的代码（将代码像数据一样进行传递）。
     * 可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升
     * */
    @Test
    public void test4() throws Exception {
        /**
         * 此处理解：
         * filterProduct方法传递了参数products和(p) -> p.getPrice() > 1000
         * 这种用法不适合一个类中多个函数的那种类型，因为它会不知道自己指定的是哪一个函数
         * */
        filterProduct(products, (p) -> p.getPrice() > 1000).forEach(System.out::println);
    }

    //获取价格从高到低排行前三的商品信息
    /**
     * 使用Stream API优化
     * */
    @Test
    public void test5() {
        //获取价格从高到低排行前三的商品信息
        /**
         * 自解：
         * sorted函数作比较，x，y代指两两比较，比较方式为y.getPrice().compareTo(x.getPrice())
         * limit(3) 取前3
         * */
        products.stream().sorted((x,y) -> y.getPrice().compareTo(x.getPrice()))
                .limit(3).forEach(System.out::println);
    }
}
