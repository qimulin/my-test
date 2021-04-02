package zhou.wu.bootswagger.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;

/**
 * 【注意】：需要事先官网下载jar包启动，并端口对应
 * 它用来连接命令行中启动的服务，告诉服务器怎么处理外界的http请求
 * @author lin.xc
 * @date 2021/4/2
 **/
public class MockServer {
    public static void main(String[] args) {

        //WireMock.configureFor(); 这个方法有很多重载 可以知道ip和端口 因为是本地开发我们不指定ip
        WireMock.configureFor(8080);//指定连接端口
//        WireMock.removeAllMappings();    //将之前做过的所有请求配置清掉

        //接下来告诉服务器怎么处理请求
        //伪造测试桩
        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo("/order/1"))
                        .willReturn(WireMock.aResponse().withBody("{\"id\":1}").withStatus(200))
        );
    }
}
