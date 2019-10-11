package zhou.wu.bootes.controller;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import zhou.wu.bootes.models.Article;
import zhou.wu.bootes.repositories.ArticleRepository;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author Lin.xc
 * @date 2019/9/20
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("")
    public Article save(@RequestBody Article article){
        return articleRepository.save(article);
    }

    @GetMapping("")
    public Iterable<Article> findAll(){
        return articleRepository.findAll();
    }

    /**---------- QueryBuilder 查询 -----------**/
    /**
     * 分页查询
     * */
    @GetMapping("/page")
    public Page<Article> range(String query,
                               @PageableDefault(page = 0, size = 5, sort = "time", direction = Sort.Direction.DESC) Pageable pageable){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if(query != null) {
            qb.must(QueryBuilders.matchQuery("title", query));
        }
        return articleRepository.search(qb, pageable);
    }

    /**
     * 精确匹配
     * 【注意】查询中文时，需要安装分词插件，查询英文没问题
     * */
    @GetMapping("/term")
    public Page<Article> term(String query){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.termQuery("content", query));
        return (Page<Article>)articleRepository.search(qb);
    }

    /**
     * 模糊匹配
     * */
    @GetMapping("/match")
    public Page<Article> match(String query){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchQuery("content", query));
        return (Page<Article>)articleRepository.search(qb);
    }

    /**
     * 短语模糊匹配
     * */
    @GetMapping("/matchPhrase")
    public Page<Article> matchPhraseQuery(String query){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchPhraseQuery("content", query));
        return (Page<Article>)articleRepository.search(qb);
    }

    /**
     * 范围查询
     * .includeLower(true)  // true  包含下界， false 不包含下界
     * .includeUpper(false)); // true  包含下界， false 不包含下界
     * */
    @GetMapping("/range")
    public Page<Article> range(long query){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.rangeQuery("time").gt(query).includeLower(true));
        //qb.must(QueryBuilders.rangeQuery("time").from(query).to(System.currentTimeMillis()));//大于query，小于当前时间
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(qb);
        return (Page<Article>)articleRepository.search(qb);
    }

    /**
     * Bulk API，批量插入
     * */
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
    }

}
