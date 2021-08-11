package zhou.wu.mytest.serialize.fastjson_bug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 反序列化测试
 * 【注意】：要测试BUG的话，注意版本控制 <= 1.2.24
 * @author lin.xc
 * @date 2021/8/11
 **/
public class FdsTest {
    public static void main(String[] args) {
        String js = "{\"@type\":\"zhou.wu.mytest.serialize.fastjson_bug.Student\",\"age\":18,\"name\":\"林七\"}";
//        String js = "{\"age\":18,\"name\":\"林七\"}";
        // 字符串转对象，未指定对象类型则会转成JSONObject格式的；若用带@type的字符串，则会转成@type指定的对象类型
        System.out.println(JSON.parseObject(js).getClass());
        // 指定对象类型，转换，两种字符串都能转成指定的对象，且会检查@type和参数clazz需要匹配上
        System.out.println(JSON.parseObject(js, Student.class).getClass());
    }
}
