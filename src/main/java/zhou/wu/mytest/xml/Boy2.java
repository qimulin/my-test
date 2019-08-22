package zhou.wu.mytest.xml;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
/**
 * Created by lin.xc on 2019/7/24
 */
@XmlRootElement // 必须的
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Boy2 {

    String name = "CY";
    @XmlElement
    int age = 10;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
