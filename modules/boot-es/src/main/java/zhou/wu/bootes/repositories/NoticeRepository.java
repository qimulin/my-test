package zhou.wu.bootes.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import zhou.wu.bootes.models.Notice;

/**
 * @author Lin.xc
 * @date 2019/9/27
 */
@Component
public interface NoticeRepository extends ElasticsearchRepository<Notice, Long> {

}
