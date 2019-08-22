package zhou.wu.mytest.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by lin.xc on 2019/7/24
 * @XmlAccessorType(XmlAccessType.PROPERTY) 意思是 只有 属性 才能被转换成 xml 中的标签。
 */
@XmlRootElement // 必须要标明这个元素
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
public class Boy1 {
    String name = "CY1";
}
