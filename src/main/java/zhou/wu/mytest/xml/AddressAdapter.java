package zhou.wu.mytest.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lin.xc on 2019/7/24
 * String转对象Address
 */
public class AddressAdapter extends XmlAdapter<String,Address> {

    @Override
    public Address unmarshal(String v) throws Exception {
        Address a = new AddressImpl();
        return a;
    }

    @Override
    public String marshal(Address v) throws Exception {
        return "转换";
    }
}
