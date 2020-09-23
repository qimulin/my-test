package zhou.wu.mytest.math;

import java.math.BigDecimal;

/**
 * 为什么阿里巴巴禁止使用BigDecimal的equals方法做等值比较？
 * @author lin.xc
 * @date 2020/9/21
 **/
public class BigDecimalTest {
    public static void main(String[] args) {

        BigDecimal bigDecimal = new BigDecimal(1);
        BigDecimal bigDecimal1 = new BigDecimal(1);
        System.out.println("equals判断1："+bigDecimal.equals(bigDecimal1));

        BigDecimal bigDecimal2 = new BigDecimal(1);
        BigDecimal bigDecimal3 = new BigDecimal(1.0);
        System.out.println("equals判断2："+bigDecimal2.equals(bigDecimal3));

        BigDecimal bigDecimal4 = new BigDecimal("1");
        BigDecimal bigDecimal5 = new BigDecimal("1.0");
        System.out.println("equals判断3："+bigDecimal4.equals(bigDecimal5));
        System.out.println("compareTo判断3："+bigDecimal4.compareTo(bigDecimal5));

        BigDecimal bigDecimal6 = new BigDecimal(0.1);
        BigDecimal bigDecimal7 = new BigDecimal(0.10);
        System.out.println("equals判断4："+bigDecimal6.equals(bigDecimal7));
        // BigDecimal.compareTo 若前比后大，则返回1，相等则返回0；不相等则返回-1
        System.out.println("compareTo判断4："+bigDecimal6.compareTo(bigDecimal7));
    }
}
/**
 * 通过以上代码示例，我们发现，在使用BigDecimal的equals方法对1和1.0进行比较的时候，
 * 有的时候是true（当使用int、double定义BigDecimal时），有的时候是false（当使用String定义BigDecimal时）
 * */
