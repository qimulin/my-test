package zhou.wu.mytest.lambda.filter;

/**
 * Created by lin.xc on 2019/7/11
 */
public class FilterProductByPrice implements MyPredicate<Product> {
    @Override
    public boolean test(Product t) {
        return t.getPrice() > 1000;
    }

//    @Override
//    public boolean test1(Product t) {
//        return t.getPrice() > 1500;
//    }
}
