package zhou.wu.mytest.xml;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.adapters.XmlAdapter;
/**
 * Created by lin.xc on 2019/7/24
 */
public class DateAdapter extends XmlAdapter<String, Date> {

    private String pattern = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat fmt = new SimpleDateFormat(pattern);

    @Override
    public Date unmarshal(String dateStr) throws Exception {

        return fmt.parse(dateStr);
    }

    @Override
    public String marshal(Date date) throws Exception {

        return fmt.format(date);
    }

}

