package zhou.wu.bootes.controller;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zhou.wu.bootes.models.User;
import zhou.wu.bootes.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lin.xc
 * @date 2019/9/20
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/add_index", method = RequestMethod.POST)
    public boolean insert(@RequestBody User user) {
        boolean falg=false;
        try{
            userRepository.save(user);
            falg=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return falg;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<User> search(@Param("searchContent") String searchContent) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(searchContent);
        System.out.println("查询的语句:"+builder);
        Iterable<User> searchResult = userRepository.search(builder);
        Iterator<User> iterator = searchResult.iterator();
        List<User> list=new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    @RequestMapping(value = "/find/page", method = RequestMethod.GET)
    public List<User> searchUser(@Param("pageNumber")Integer pageNumber,
                                 @Param("pageSize")Integer pageSize,
                                 @Param("searchContent")String searchContent) {
        // 分页参数
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(searchContent);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(builder).build();
        System.out.println("查询的语句:" + searchQuery.getQuery().toString());
        Page<User> searchPageResults = userRepository.search(searchQuery);
        return searchPageResults.getContent();
    }

    public List<User> searchUserByWeight(String searchContent) {
    /*    // 根据权重进行查询
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.boolQuery())
                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name", searchContent)),
                        ScoreFunctionBuilders.weightFactorFunction(10))
                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("description", searchContent)),
                        ScoreFunctionBuilders.weightFactorFunction(100)).setMinScore(2);
        System.out.println("查询的语句:" + functionScoreQueryBuilder.toString());
        Iterable<User> searchResult = userRepository.search(functionScoreQueryBuilder);
        Iterator<User> iterator = searchResult.iterator();
        List<User> list=new ArrayList<User>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;*/
        return null;
    }
}
