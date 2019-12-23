package zhou.wu.aliyunes.coma.test67;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.*;
import zhou.wu.aliyunes.coma.EsConstant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lin.xc
 * @date 2019/12/11
 */
public class RestClientTest67 {
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
            // 创建request。
            Map<String, Object> jsonMap = new HashMap<>();
            // field_01、field_02为字段名，value_01、value_02为对应的值。
            jsonMap.put("{field_01}", "{value_01}");
            jsonMap.put("{field_02}", "{value_02}");
            //index_name为索引名称；type_name为类型名称；doc_id为文档的id。
            IndexRequest indexRequest = new IndexRequest("{index_name}", "{type_name}", "{doc_id}").source(jsonMap);

            // 同步执行，并使用自定义RequestOptions（COMMON_OPTIONS）。
            IndexResponse indexResponse = highClient.index(indexRequest, COMMON_OPTIONS);

            long version = indexResponse.getVersion();

            System.out.println("Index document successfully! " + version);
            //index_name为索引名称；type_name为类型名称；doc_id为文档的id。与以上创建索引的名称和id相同。
            DeleteRequest request = new DeleteRequest("{index_name}", "{type_name}", "{doc_id}");
            DeleteResponse deleteResponse = highClient.delete(request, COMMON_OPTIONS);

            System.out.println("Delete document successfully! \n" + deleteResponse.toString() + "\n" + deleteResponse.status());

            highClient.close();

        } catch (IOException ioException) {
            // 异常处理。
        }
    }
}
