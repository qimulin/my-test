package zhou.wu.aliyunes.coma.test67;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import zhou.wu.aliyunes.coma.EsConstant;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Lin.xc
 * @date 2019/12/11
 */
public class RestClientSearchTest67 {
    private static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        // 默认缓存限制为100MB，此处修改为30MB。
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory
                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        // 阿里云ES集群需要basic auth验证。
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //访问用户名和密码为您创建阿里云Elasticsearch实例时设置的用户名和密码，也是Kibana控制台的登录用户名和密码。
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(EsConstant.USERNAME, EsConstant.PASSWORD));

        // 通过builder创建rest client，配置http client的HttpClientConfigCallback。
        // 单击所创建的Elasticsearch实例ID，在基本信息页面获取公网地址，即为ES集群地址。
        RestClientBuilder builder = RestClient.builder(new HttpHost(EsConstant.ADDRESS, EsConstant.PORT))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback(){
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                        return builder.setConnectTimeout(60000).setSocketTimeout(1200000);
                    }
                } );

        // RestHighLevelClient实例通过REST low-level client builder进行构造。
        RestHighLevelClient highClient = new RestHighLevelClient(builder);

        try {
            // 各种场景
//            countTotal(highClient);
//            countRangeTotal(highClient);
//            pageRecord(highClient);
//            aggregation(highClient);
            createNewIndex(highClient);
//            statHourGroupTodayOrderTotal(highClient);
            highClient.close();

        } catch (IOException ioException) {
            // 异常处理。
        }
    }

    public static final String INDEX_NAME = "order_pay_test";
    public static final String TYPE_NAME = "order_pay_test";
    public static final String INDEX_NAME_1 = "order_pay";
    public static final String TYPE_NAME_1 = "order_pay";

    /**
     * 统计数量
     * */
    @SuppressWarnings("Duplicates")
    private static void countTotal(RestHighLevelClient highLevelClient){
        // 查询当前索引的文档总数
        CountRequest countRequest = new CountRequest(INDEX_NAME,INDEX_NAME_1);
        CountResponse response;
        try {
            long startTime=System.currentTimeMillis();
            response = highLevelClient.count(countRequest,COMMON_OPTIONS);
            long endTime=System.currentTimeMillis();
            System.out.println("统计数量执行耗时："+getExeTime(startTime,endTime));
            long count = response.getCount();
            System.out.println("当前索引文档数为："+count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计范围内数量
     * */
    @SuppressWarnings("Duplicates")
    private static void countRangeTotal(RestHighLevelClient highLevelClient){
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 设置一些公共参数
        setCommonCondition(boolBuilder);
        // 设置查询，可以是任何类型的QueryBuilder。
        sourceBuilder.query(boolBuilder);
        // 查询当前索引的文档总数
        CountRequest countRequest = new CountRequest(INDEX_NAME);
        countRequest.source(sourceBuilder);
        CountResponse response;
        try {
            long startTime=System.currentTimeMillis();
            response = highLevelClient.count(countRequest,COMMON_OPTIONS);
            long endTime=System.currentTimeMillis();
            System.out.println("统计范围数量执行耗时："+getExeTime(startTime,endTime));
            long count = response.getCount();
            System.out.println("当前索引文档数为："+count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询数据
     * */
    @SuppressWarnings("Duplicates")
    private static void pageRecord(RestHighLevelClient highLevelClient){
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 设置一些公共参数
        setCommonCondition(boolBuilder);
        // 设置查询，可以是任何类型的QueryBuilder。
        sourceBuilder.query(boolBuilder);
        // 设置确定结果要从哪个索引开始搜索的from选项，默认为0
        sourceBuilder.from(10000);
        // 设置确定搜素命中返回数的size选项，默认为10
        sourceBuilder.size(20);
        // 设置一个可选的超时，控制允许搜索的时间
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 排序
        sourceBuilder.sort("id",SortOrder.DESC);

        // 返回部分字段
        String[] fields = {"id","merchantId","createTime"};
        FetchSourceContext sourceContext = new FetchSourceContext(true,fields, Strings.EMPTY_ARRAY);
//        FetchSourceContext sourceContext = new FetchSourceContext(true);
        sourceBuilder.fetchSource(sourceContext);

        // 索引
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME,INDEX_NAME_1);
        // 类型
        searchRequest.types(TYPE_NAME,TYPE_NAME_1);
        searchRequest.source(sourceBuilder);
        try {
            long startTime=System.currentTimeMillis();
            SearchResponse response = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            long endTime=System.currentTimeMillis();
            System.out.println("分页查询执行耗时："+getExeTime(startTime,endTime));
            SearchHits hits = response.getHits();  //SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数：
            SearchHit[] searchHits = hits.getHits();
            System.out.println("数据条数="+searchHits.length);
//            for (SearchHit hit : searchHits) {
//                System.out.println("search -> "+hit.getSourceAsString());
//            }
            System.out.println("SearchHit[] -> "+ Arrays.toString(searchHits));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 聚合操作
     * */
    @SuppressWarnings("Duplicates")
    private static void aggregation(RestHighLevelClient highLevelClient){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_payType")
//                .field("payType");
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_merchantId")
                .field("merchantId");
        String groupStr = "17";
        aggregation.subAggregation(AggregationBuilders.sum("sum_amount")
                .field("amount"));
        searchSourceBuilder.aggregation(aggregation);

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        // 新建range条件
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime");
        // 开始时间
        rangeQueryBuilder.gte(DateUtils.parseDate("2019-10-22 00:00:00",new String[]{"yyyy-MM-dd HH:mm:ss"}));
        // 结束时间
        rangeQueryBuilder.lt(DateUtils.parseDate("2019-12-12 23:59:59",new String[]{"yyyy-MM-dd HH:mm:ss"}));

        // 设置查询，可以是任何类型的QueryBuilder。
        boolBuilder.must(rangeQueryBuilder);
        // 设置查询，可以是任何类型的QueryBuilder。
        searchSourceBuilder.query(boolBuilder);

        // 索引
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME,INDEX_NAME_1);
        // 类型
        searchRequest.types(TYPE_NAME,TYPE_NAME_1);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            long startTime=System.currentTimeMillis();
            searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            long endTime=System.currentTimeMillis();
            System.out.println("分组统计执行耗时："+getExeTime(startTime,endTime));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Aggregations aggregations = searchResponse.getAggregations();
//        Terms groupAggregation = aggregations.get("by_payType");
        Terms groupAggregation = aggregations.get("by_merchantId");
        Terms.Bucket elasticBucket = groupAggregation.getBucketByKey(groupStr);
        Sum sum_amount = elasticBucket.getAggregations().get("sum_amount");
//        double sumAmount = sum_amount.getValue();
        String sumStr = String.format("%.2f", sum_amount.getValue());
        BigDecimal sumAmount = new BigDecimal(sumStr);
        System.out.println("分组值为【"+groupStr+"】金额总数为"+sumAmount);
    }

    /**
     * 获取执行时间
     * */
    private static float getExeTime(long startTime,long endTime){
        return (float)(endTime-startTime);
    }


    /**
     * 设置共同查询条件
     * */
    @SuppressWarnings("Duplicates")
    private static void setCommonCondition(BoolQueryBuilder boolBuilder){
        // 新建range条件
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime");
        // 开始时间
        rangeQueryBuilder.gte(DateUtils.parseDate("2019-09-12 00:00:00",new String[]{"yyyy-MM-dd HH:mm:ss"}));
        // 结束时间
        rangeQueryBuilder.lt(DateUtils.parseDate("2019-12-12 23:59:59",new String[]{"yyyy-MM-dd HH:mm:ss"}));
        // 设置查询，可以是任何类型的QueryBuilder。
        boolBuilder.must(rangeQueryBuilder);
        boolBuilder.must(QueryBuilders.termQuery("merchantId", 17));
    }


    /**
     * 统计今日订单总额
     * */
    @SuppressWarnings("Duplicates")
    private static void statHourGroupTodayOrderTotal(RestHighLevelClient highLevelClient){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String aggGroup = "by_hour";
        String countGroup = "count_id";
//        TermsAggregationBuilder aggregation =
//        DateHistogramAggregationBuilder dateHistogramAggregationBuilder = AggregationBuilders.dateHistogram(aggGroup)
//                .field("createTime").dateHistogramInterval(DateHistogramInterval.HOUR);
        AbstractAggregationBuilder aggregation = AggregationBuilders.dateHistogram(aggGroup).field("createTime")
                .dateHistogramInterval(DateHistogramInterval.HOUR).format("HH");
        aggregation.subAggregation(AggregationBuilders.count(countGroup)
                .field("id"));
        searchSourceBuilder.aggregation(aggregation);

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        // 新建range条件
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime");
        // 开始时间
        rangeQueryBuilder.gte(DateUtils.parseDate("2019-12-12 00:00:00",new String[]{"yyyy-MM-dd HH:mm:ss"}));
        // 结束时间
        rangeQueryBuilder.lt(DateUtils.parseDate("2019-12-12 23:59:59",new String[]{"yyyy-MM-dd HH:mm:ss"}));

        // 设置查询，可以是任何类型的QueryBuilder。
        boolBuilder.must(rangeQueryBuilder);
        // 单商户
        boolBuilder.must(QueryBuilders.termQuery("merchantId", 17));
//        boolBuilder.must(QueryBuilders.termsQuery("merchantId", 17));
        // 设置查询，可以是任何类型的QueryBuilder。
        searchSourceBuilder.query(boolBuilder);

        // 索引
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME,INDEX_NAME_1);
        // 类型
        searchRequest.types(TYPE_NAME,TYPE_NAME_1);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            long startTime=System.currentTimeMillis();
            searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            long endTime=System.currentTimeMillis();
            System.out.println("分组统计执行耗时："+getExeTime(startTime,endTime));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Aggregations aggregations = searchResponse.getAggregations();
        Histogram timeAgg  = aggregations.get(aggGroup);
        for(Histogram.Bucket entry:timeAgg.getBuckets()){
            ParsedValueCount parsedValueCount = entry.getAggregations().get(countGroup);
            System.out.println("分组："+entry.getKeyAsString()+"-----"+parsedValueCount.getValue());
        }
        System.out.println("结尾！");
    }

    /**
     * 统计今日订单总额
     * */
    @SuppressWarnings("Duplicates")
    private static void createNewIndex(RestHighLevelClient highLevelClient){
        CreateIndexRequest request = new CreateIndexRequest("linxc_index_3201");
        request.settings(Settings.builder()
                // 分片数
                .put("index.number_of_shards", 5)
                // 副本数
                .put("index.number_of_replicas", 1)
        );
        try {
            highLevelClient.indices().create(request,COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("插入完毕！");
    }

}
