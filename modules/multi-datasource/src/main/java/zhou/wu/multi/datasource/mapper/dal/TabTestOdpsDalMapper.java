package zhou.wu.multi.datasource.mapper.dal;

import zhou.wu.multi.datasource.domain.TabTest;

import java.util.List;
import java.util.Map;

public interface TabTestOdpsDalMapper {

    TabTest getById(Long id);

    List<Map<String, Object>> showTables();
}