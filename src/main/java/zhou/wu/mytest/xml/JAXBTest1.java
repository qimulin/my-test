package zhou.wu.mytest.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * Created by lin.xc on 2019/7/24
 */
public class JAXBTest1 {

    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Boy1.class);

        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Boy1 boy = new Boy1();
        // 先是marshall成 xml文件，
        marshaller.marshal(boy, System.out);
        System.out.println();
        System.out.println("-------------------------");
        String xml = "<boy1><name>David1</name></boy1>";
        // 再是把 xml 文件 unmarshal 成 java object。
        // java object 转换成 xml 的时候，Boy1中的name不是属性（因为没有 get set方法），所以name不转换成标签。
        // 给name属性添加 get set 方法,结果xml中就会有name了
        Boy1 boy2 = (Boy1) unmarshaller.unmarshal(new StringReader(xml));
        System.out.println(boy2.name);
    }
}
