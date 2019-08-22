package zhou.wu.mytest.xml;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
/**
 * Created by lin.xc on 2019/7/24
 * 对于根元素，可以设置属性：
 * @XmlRootElement(name="b" nameSpace="http://test")
 * 这样，在生成的xml文件中，<boy> 标签 就会变为 <b> 标签。并且加上一个命名空间。
 */
//@XmlRootElement // 必须要标明这个元素
@XmlRootElement(name="b",namespace = "http://test")
@XmlAccessorType(XmlAccessType.FIELD)
public class Boy {
    String name = "CY";
}
