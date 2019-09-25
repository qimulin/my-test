package zhou.wu.bootes.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
/**
 * @author Lin.xc
 * @date 2019/9/20
 */
@Data
@Document(indexName = "location")
public class Location {
    @Id
    private String id;

    /**
     * 位置坐标 lon经度 lat纬度
     * */
    @GeoPointField
    private GeoPoint location;

    /**
     * 地址
     * */
    private String address;

    /**
     * 距离多少米
     * */
    private String distanceMeters;
}
