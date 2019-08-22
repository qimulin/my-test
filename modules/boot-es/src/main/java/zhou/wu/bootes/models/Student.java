package zhou.wu.bootes.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by lin.xc on 2019/8/2
 */
@Data
@ToString
@Document(indexName = "student", type = "student", shards = 1, replicas = 0, refreshInterval = "-1")
public class Student {
    @Id
    private String sId;
    private String sName;
    private String addr;
}
