package zhou.wu.bootes.controller;

//import com.google.common.collect.Lists;
//import org.elasticsearch.action.bulk.BulkRequestBuilder;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
//import zhou.wu.bootes.models.Notice;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author Lin.xc
 * @date 2019/9/27
 */
@RestController
@RequestMapping("/article/native")
public class ArticleNativeController {
/*
    @Autowired
    private TransportClient transportClient;

    *//**
     * Bulk API，批量插入
     * *//*
    @PostMapping("/batch/bulk")
    public Boolean batchInsert(){
//        System.out.println(articles.size());
        BulkRequestBuilder bulkRequest = null;
        String index = "article";
        String type = "test";
        Date date = new Date();
        try {
            bulkRequest = transportClient.prepareBulk();
// either use client#prepare, or use Requests# to directly build index/delete requests
            // id默认
            bulkRequest.add(transportClient.prepareIndex(index, type,String.valueOf(date.getTime()))
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("id", "19092705")
                            .field("author", "Lin.batch Book")
                            .field("title", "Lin.batch Title")
                            .field("content", "Lin.batch Content")
                            .field("time", date.getTime())
                            .endObject()
                    )
            );
            date = new Date();
            bulkRequest.add(transportClient.prepareIndex(index, type, String.valueOf(date.getTime()))
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("id", "19092706")
                            .field("author", "Yu.batch Book")
                            .field("title", "Yu.batch Title")
                            .field("content", "Yu.batch Content")
                            .field("time", date.getTime())
                            .endObject()
                    )
            );
//            bulkRequest.add(client.prepareIndex(index, type, "1909270302")
//                    .setSource(jsonBuilder()
//                            .startObject()
//                            .field("id", "19092704")
//                            .field("author", "Yu.batch Book")
//                            .field("title", "Yu.batch Title")
//                            .field("content", "Yu.batch Content")
//                            .field("time", date.getTime())
//                            .endObject()
//                    )
//            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            //处理失败
            System.out.println("批量插入失败！错误信息："+bulkResponse.buildFailureMessage());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }*/
}
