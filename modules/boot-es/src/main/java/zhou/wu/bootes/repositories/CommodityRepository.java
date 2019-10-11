package zhou.wu.bootes.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import zhou.wu.bootes.models.Commodity;

/**
 * @author Lin.xc
 * @date 2019/9/29
 */
@Repository
public interface CommodityRepository extends ElasticsearchRepository<Commodity, String> {

}
