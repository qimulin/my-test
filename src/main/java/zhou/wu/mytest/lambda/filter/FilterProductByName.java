package zhou.wu.mytest.lambda.filter;

/**
 * Created by lin.xc on 2019/7/11
 */
public class FilterProductByName implements MyPredicate<Product> {
    @Override
    public boolean test(Product t) {
        return t.getName().contains("手机");
    }

//    @Override
//    public boolean test1(Product t) {
//        return t.getName().contains("笔记本");
//    }
}
