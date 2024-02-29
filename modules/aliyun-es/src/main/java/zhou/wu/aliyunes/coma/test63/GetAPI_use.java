
package zhou.wu.aliyunes.coma.test63;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import zhou.wu.aliyunes.coma.EsConstant;

import java.io.IOException;

public class GetAPI_use {

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
            GetRequest request = new GetRequest("apitest_index", "apitest_type", "1");
            GetResponse result = client.get(request);
            System.out.println("请求结果："+result);
        } catch(Throwable e) {
            e.printStackTrace();
        } finally {
            client.close();
        }

    }
}