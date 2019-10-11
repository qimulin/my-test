package zhou.wu.bootes.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Lin.xc
 * @date 2019/9/27
 * indexName代表索引名称,type代表表名称
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "notice_index", type = "doc")
public class Notice {

    //id
    /**
     *
     * */
    @JsonProperty("auto_id")
    private Long id;

    //标题
    /**
     *
     * */
    @JsonProperty("title")
    private String title;

    //
    /**
     *
     * */
    @JsonProperty("exchange_mc")
    private String exchangeMc;

    /**
     * 公告发布时间
     * */
    @JsonProperty("create_time")
    private String originCreateTime;

    /**
     * 公告阅读数量
     * */
    @JsonProperty("read_count")
    private Integer readCount;

}