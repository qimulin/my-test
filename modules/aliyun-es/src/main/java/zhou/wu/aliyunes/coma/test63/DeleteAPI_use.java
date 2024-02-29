
package zhou.wu.aliyunes.coma.test63;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import zhou.wu.aliyunes.coma.EsConstant;

import java.io.IOException;

public class DeleteAPI_use {

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
        	DeleteRequest request = new DeleteRequest("apitest_index", "apitest_type", "1");
        	DeleteResponse result = client.delete(request);
            System.out.println("请求结果："+result);
        } catch(Throwable e) {
            e.printStackTrace();
        } finally {
            client.close();
        }

    }
}