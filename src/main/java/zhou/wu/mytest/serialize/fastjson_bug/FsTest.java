package zhou.wu.mytest.serialize.fastjson_bug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 序列化测试
 * @author lin.xc
 * @date 2021/8/11
 **/
public class FsTest {
    public static void main(String[] args) {
        Student student = new Student();
        student.setAge(18);
        student.setName("林七");
        // 加了SerializerFeature.WriteClassName参数，转字符串的时候会多处@type字段来记录其对应的类名
        String js1 = JSON.toJSONString(student, SerializerFeature.WriteClassName);
        // 普通的转
        String js2 = JSON.toJSONString(student);
        System.out.println(js1);
        System.out.println(js2);
    }
}
