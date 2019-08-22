package zhou.wu.mytest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.apache.commons.lang3.StringUtils;

/**
 * Map集合参数 ASCII 码从小到大排序
 * @author javaxie
 * @since 2018-08-10
 * @version 1.0
 *
 */

public class MapSortUtil {

    /**
     * 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
     * @author javaxie
     * @param paraMap    要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写
     * @param removeEmptyValue 是否移除空值排序
     * @return
     */
    public static String formatUrlMap(Map<String, String> paramMap, boolean urlEncode, boolean keyToLower, boolean removeEmptyValue) {
        String buff = "";
        Map<String, String> tmpMap = paramMap;
        //开启空值筛选，则移除数据
        if(removeEmptyValue){
//            MapRemoveNullUtil.removeNullEntry(tmpMap);
        }
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (null!=item.getKey() && !"".equals(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    }
                    else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }
            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    /**
     * javaxie测试Map集合参数ASSCII由小到大排序
     */
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("mchId", "888888");
        map.put("mchName", "javaxie");
        map.put("mchOrderId", "201808101005558898666");
        map.put("mchOrderIp", "127.0.0.1");
        map.put("notify_url", "http://www.fujaja.com");
        map.put("return_url", "http://www.fujaja.com");
        map.put("money", "88.88");
        map.put("sign", "613cc878bfa8432f85ea85f795ca4028");
        map.put("channelId", "1000");
        System.out.println(MapSortUtil.formatUrlMap(map, false, false, true));
    }
}