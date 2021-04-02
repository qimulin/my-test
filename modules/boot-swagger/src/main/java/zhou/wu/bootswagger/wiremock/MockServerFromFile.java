package zhou.wu.bootswagger.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * @author lin.xc
 * @date 2021/4/2
 **/
public class MockServerFromFile {

    public static void main(String[] args) throws IOException {
        //WireMock.configureFor(); 这个方法有很多重载 可以知道ip和端口 因为是本地开发我们不指定ip
        WireMock.configureFor(8080);//指定连接端口
        WireMock.removeAllMappings();    //将之前做过的所有请求配置清掉

        /**
         ClassPathResource resource = new ClassPathResource("mock/respose/01.txt");

         String content = FileUtils.readFileToString(resource.getFile());//将文件里的内容读成string  不推荐了
         content = FileUtils.readFileToString(resource.getFile(), "utf-8"); //也不推荐

         List<String> readLines = FileUtils.readLines(resource.getFile(), "utf-8");
         content = StringUtils.join(readLines,"/r");

         mock(url,fiel);

         //接下来告诉服务器怎么处理请求
         //伪造测试桩
         WireMock.stubFor(
         WireMock.get(WireMock.urlEqualTo("/order/1"))
         .willReturn(WireMock.aResponse().withBody("{\"id\":1}").withStatus(200))
         );

         // 抽取上面代码为一个方法
         **/
        mock("/order?and=query","01");
        mock("/order/urlPath","02");
    }

    public static void mock(String url,String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/"+file+".txt");
        List<String> readLines = FileUtils.readLines(resource.getFile(), "utf-8");
        //String content = StringUtils.join(readLines.toArray());
        String content = StringUtils.join(readLines.toArray(),"\n");
        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo(url))
                        .willReturn(WireMock.aResponse().withBody(content).withStatus(200))
        );

    }
}
