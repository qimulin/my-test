package zhou.wu.bootes.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import zhou.wu.bootes.models.User;

/**
 * @author Lin.xc
 * @date 2019/9/20
 */
public interface UserRepository extends ElasticsearchRepository<User, Long> {
}
