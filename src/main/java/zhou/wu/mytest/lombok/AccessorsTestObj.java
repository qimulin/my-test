package zhou.wu.mytest.lombok;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by lin.xc on 2019/7/17
 * @Accessors 因此有3个选择：
 * 1. fluent 一个布尔值。如果为真，pepper的getter就是 pepper()，setter方法就是pepper(T newValue)。并且，除非特别说明，chain默认为真。
 * 2. chain 一个布尔值。如果为真，产生的setter返回的this而不是void。默认是假。如果fluent=true，那么chain默认为真。
 * set方法返回的是对象的实例，因此可以直接再使用set方法或者直接调用函数
 * 3. prefix 一系列string类型。如果显示，属性必须加上某些定义的前缀。每个属性名反过来与列表中的每个前缀进行比较，一个找到一个匹配，
 *  这个前缀被提取出来为属性创建基本的名字。前缀列表中不包含任何前缀也是合法的，为空则总是匹配。字符都是字母，
 *  紧接着前缀后的字符一定不能是小写字母。例如，pepper对前缀p不是相等匹配，而跟pEpper是匹配的(也就意味着属性的基本名字是epper)。
 */
//@Accessors(fluent=true)
@Accessors(chain=true)
//@Accessors(prefix="obj")
@Data
public class AccessorsTestObj {
    private Long objId;
    @Accessors(prefix="obj")
    private String objName;
}
/**
 * fluent的中文含义是流畅的，设置为true，则getter和setter方法的方法名都是基础属性名，且setter方法返回当前对象。如下
 * chain的中文含义是链式的，设置为true，则setter方法返回当前对象。如下
 * prefix的中文含义是前缀，用于生成getter和setter方法的字段名会忽视指定前缀（遵守驼峰命名）
 * @Accessors(prefix="obj") 也可单独修饰字段
 * */
