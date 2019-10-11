package zhou.wu.bootes.controller;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;
import zhou.wu.bootes.models.Commodity;
import zhou.wu.bootes.repositories.CommodityRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin.xc
 * @date 2019/9/29
 */
@RequestMapping("/commodity")
@RestController
public class CommodityController {

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    public ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("/count")
    public long count() {
        return commodityRepository.count();
    }

    @PostMapping("")
    public Commodity save(@RequestBody Commodity commodity) {
        return commodityRepository.save(commodity);
    }

    @DeleteMapping("")
    public void delete(Commodity commodity) {
        commodityRepository.delete(commodity);
//        commodityRepository.deleteById(commodity.getSkuId());
    }

    @GetMapping("/all")
    public Iterable<Commodity> getAll() {
        return commodityRepository.findAll();
    }

    @GetMapping("/by-name")
    public List<Commodity> getByName(String name) {
        List<Commodity> list = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", name);
        Iterable<Commodity> iterable = commodityRepository.search(matchQueryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }

    @GetMapping("/page")
    public Page<Commodity> pageQuery(Integer pageNo, Integer pageSize, String kw) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", kw))
                .withPageable(PageRequest.of(pageNo, pageSize))
                .build();
        return commodityRepository.search(searchQuery);
    }

    /****************** 使用ElasticsearchTemplate **********************/
    @PostMapping("/by-template")
    public String saveByTemplate(@RequestBody Commodity commodity) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(commodity).build();
        return elasticsearchTemplate.index(indexQuery);
    }

    @GetMapping("/by-template")
    public List<Commodity>  queryByTemplate(String name) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", name))
                .build();
        List<Commodity> list = elasticsearchTemplate.queryForList(searchQuery, Commodity.class);
        return list;
    }
}
