package zhou.wu.bootes.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
/**
 * @author Lin.xc
 * @date 2019/9/20
 */
@Data
@Document(indexName = "article", type = "test")
public class Article {
    @Id
    private String id;
    private String author;
    private String title;
    private String content;
    private Date time;
}