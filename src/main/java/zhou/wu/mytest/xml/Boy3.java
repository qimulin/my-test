package zhou.wu.mytest.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by lin.xc on 2019/7/24
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Boy3 {
    private String name = "CY";

    @XmlJavaTypeAdapter(value=DateAdapter.class)
    private Date purDate;   // 这种@XmlJavaTypeAdapter修饰的不允许自己实现get方法

    public void setPurDate(Date purDate) {
        this.purDate = purDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
