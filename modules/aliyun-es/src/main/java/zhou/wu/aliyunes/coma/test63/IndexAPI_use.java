package zhou.wu.aliyunes.coma.test63;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import zhou.wu.aliyunes.coma.EsConstant;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class IndexAPI_use {

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) throws IOException {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(EsConstant.USERNAME, EsConstant.PASSWORD));


        RestClientBuilder builder = RestClient.builder(new HttpHost(EsConstant.ADDRESS, EsConstant.PORT))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });


        RestHighLevelClient client = new RestHighLevelClient(builder);

        try {
            IndexRequest request = new IndexRequest();
            request.index("apitest_index");
            request.type("apitest_type");
            request.id("1");
            Map<String, Object> source = new HashMap<>();
            source.put("user", "kimchy");
            source.put("post_date", new Date());
            source.put("message", "trying out Elasticsearch");
            request.source(source);
            try {
                IndexResponse result = client.index(request, RequestOptions.DEFAULT);
                System.out.println("请求结果："+result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            client.close();
        }

    }
}