package zhou.wu.bootes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by lin.xc on 2019/8/2
 * 基础包的注释驱动配置，配置自动扫描的repositories根目录
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "zhou.wu.bootes.repositories")
public interface ESConfig {
}
