package zhou.wu.mytest.xml;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 * Created by lin.xc on 2019/7/24
 */
public class JAXBTest {

    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Boy.class);

        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Boy boy = new Boy();
        // 先是marshall成 xml文件，
        marshaller.marshal(boy, System.out);
        System.out.println();
        System.out.println("-------------------------");
//        String xml = "<boy><name>David</name></boy>";
        String xml = "<ns2:b xmlns:ns2=\"http://test\"><name>David</name></ns2:b>";
//        String xml = "<boy name=<{b}> namespace = <{http://test}>><name>David</name></boy>";
        // 再是把 xml 文件 unmarshal 成 java object。
        Boy boy2 = (Boy) unmarshaller.unmarshal(new StringReader(xml));
        System.out.println(boy2.name);
    }
}
