
package zhou.wu.aliyunes.coma.test63;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import zhou.wu.aliyunes.coma.EsConstant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateAPI_use {

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
            UpdateRequest updateRequest = new UpdateRequest("apitest_index", "apitest_type", "1");
            IndexRequest indexRequest = new IndexRequest("apitest_index", "apitest_type", "1");
            Map<String, String> source = new HashMap<>();
            source.put("user", "dingw2");
            indexRequest.source(source);
            updateRequest.doc(indexRequest);
            UpdateResponse result = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println("请求结果："+result);
        }catch (IOException e) {
                e.printStackTrace(); 
        } finally {
        	client.close();
        }

    }
}