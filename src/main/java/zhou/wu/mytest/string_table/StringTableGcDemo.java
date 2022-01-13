package zhou.wu.mytest.string_table;

/**
 * 演示StringTable垃圾回收
 * -Xmx10m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -verbose:gc
 * -XX:+PrintStringTableStatistics 打印StringTable表的统计信息
 * -XX:+PrintGCDetails -verbose:gc 打印垃圾回收的一些详细信息
 */
public class StringTableGcDemo {
    public static void main(String[] args) {
        int i = 0;
        try {
            for (int j = 0; j < 10000; j++) {
                String.valueOf(j).intern();
                i++;
            }
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            System.out.println(i);
        }
    }
}
