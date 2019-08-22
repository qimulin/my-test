package zhou.wu.mytest.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Date;

/**
 * Created by lin.xc on 2019/7/24
 */
public class JAXBTest3 {

    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Boy3.class);

        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Boy3 boy = new Boy3();
        boy.setPurDate(new Date());
        // 先是marshall成 xml文件，
        marshaller.marshal(boy, System.out);
        System.out.println();
        System.out.println("-------------------------");
        String xml = "<boy3><name>David3</name></boy3>";
        // 再是把 xml 文件 unmarshal 成 java object。
        /**
         * 在 java object 转换成 xml 的时候，接口Address 无法被转换。
         * 所以 这里要加上 @XmlJavaTypeAdapter(AddressAdapter.class)
         * 所以 要多写一个AddressAdaptor 类。
         * 这个类会返回Address接口的一个具体实现类的对象。
         * */
        Boy3 boy3 = (Boy3) unmarshaller.unmarshal(new StringReader(xml));
        System.out.println(boy3.getName());
    }
}
