package zhou.wu.boot.web.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author zhou.wu
 * @date 2024/7/1
 *
 * - 定制 Tomcat 服务器行为：这个类可以用来修改 Tomcat 服务器的默认行为，例如调整超时时间、设置连接参数、启用或禁用特定功能等。
 * - 调试和日志：在这个示例中，该类的 customize 方法仅用于打印出 Tomcat 默认的 asyncTimeout 值，通常这是用于调试或日志输出。
 * - 扩展和配置：你可以在这个 customize 方法中添加更多自定义配置，例如：
 * -- 修改 session 超时设置
 * -- 配置 SSL 证书
 * -- 设置最大线程数、连接超时等 Tomcat 参数
 **/
@Component
public class TomcatCustomizer  implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    /**
     * 方法中打印了 Tomcat 的异步请求超时时间（asyncTimeout），其默认值通过 factory.getSession().getTimeout() 获取。
     * */
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        System.out.println("Tomcat asyncTimeout default value: " + factory.getSession().getTimeout());
    }
}
