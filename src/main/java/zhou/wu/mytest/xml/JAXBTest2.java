package zhou.wu.mytest.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * Created by lin.xc on 2019/7/24
 */
public class JAXBTest2 {

    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Boy2.class);

        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Boy2 boy = new Boy2();
        // 先是marshall成 xml文件，
        marshaller.marshal(boy, System.out);
        System.out.println();
        System.out.println("-------------------------");
        String xml = "<boy2><name>David2</name></boy2>";
        // 再是把 xml 文件 unmarshal 成 java object。
        // Boy2中的age是不会被转化到xml文件中的，除非加@XmlElement注解
        Boy2 boy2 = (Boy2) unmarshaller.unmarshal(new StringReader(xml));
        System.out.println(boy2.name);
    }
}
