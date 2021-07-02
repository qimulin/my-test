package zhou.wu.mytest.math.bit_operation;

/**
 * 位运算（计算机中运算都是以补码进行运算，正数补码等于原码）
 * 反码：就是在原码的基础上，符号位不变，其余位取反符号位不变，其余位取反符号位不变，其余位取反
 * 补码：负数的补码就是反码加1，正数的补码就是原码本身负数的补码就是反码加1，正数的补码就是原码本身负，数的补码就是反码加1，正数的补码就是原码本身
 * @author lin.xc
 * @date 2021/7/2
 **/
public class BitOperation {
    public static void main(String[] args) {
        // 二进制5对于32位而言，是 0000 0000 0000 0000 0000 0000 0000 0101
        int param =5;
        // 二进制-5对于32位而言，是 1000 0000 0000 0000 0000 0000 0000 0101
        int param1=-5;
        /* 带符号的位运算
        * 按二进制形式把所有的数字向指定方向移动，最高位保持原来的符号值。
        * 左移低位都是补0
        * */
        // 正数0000 0000 0000 0000 0000 0000 0000 0101 变为 0000 0000 0000 0000 0000 0000 0000 1010 =10（十进制）
        int param_left = param << 1;
        System.out.println("param_left="+param_left);
        /*
         * 负数-5左移一位
         * 原码：1000 0000 0000 0000 0000 0000 0000 0101
         * 反码：1111 1111 1111 1111 1111 1111 1111 1010
         * 补码：1111 1111 1111 1111 1111 1111 1111 1011
         * 补码左移一位（低位补0）：1111 1111 1111 1111 1111 1111 1111 1010
         * 左移后反码（补码-1）：1111 1111 1111 1111 1111 1111 1111 0101
         * 左移后原码（反码符号不变，其余取反）：1000 0000 0000 0000 0000 0000 0000 1010 = -10(十进制)
         * */
        int param1_left = param1 << 1;
        System.out.println("param1_left="+param1_left);

        // 正数0000 0000 0000 0000 0000 0000 0000 0101 变为 0000 0000 0000 0000 0000 0000 0000 0010 =2（十进制）
        int param_right = param >> 1;
        System.out.println("param_right="+param_right);
        /*
         * 负数-5右移一位
         * 原码：1000 0000 0000 0000 0000 0000 0000 0101
         * 反码：1111 1111 1111 1111 1111 1111 1111 1010
         * 补码：1111 1111 1111 1111 1111 1111 1111 1011
         * 补码右移一位（高位补符号位）：1111 1111 1111 1111 1111 1111 1111 1101
         * 右移后反码（补码-1）：1111 1111 1111 1111 1111 1111 1111 1100
         * 右移后原码（反码符号不变，其余取反）：1000 0000 0000 0000 0000 0000 0000 0011 = -3(十进制)
         * */
        int param1_right = param1 >> 1;
        System.out.println("param1_right="+param1_right);

        // 正数无符号右移和带符号右移无区别，因为都是高位补0
        int param_n_right = param >>> 1;
        System.out.println("param_n_right="+param_n_right);
        /*
        * 负数-5不带符号右移一位
        * 原码：1000 0000 0000 0000 0000 0000 0000 0101
        * 反码：1111 1111 1111 1111 1111 1111 1111 1010
        * 补码：1111 1111 1111 1111 1111 1111 1111 1011
        * 补码无符号右移一位：0111 1111 1111 1111 1111 1111 1111 1101
        * 此时补码为正数，则补码即为这个数的原码，
        * 原码：0111 1111 1111 1111 1111 1111 1111 1101 = 2147483645（十进制）
        * */
        int param1_n_right = param1 >>> 1;
        System.out.println("param1_n_right="+param1_n_right);
    }
}