package zhou.wu.multi.datasource.mapper.dal;

import org.apache.ibatis.annotations.Param;
import zhou.wu.multi.datasource.domain.UserInfo;

import java.util.Map;

public interface UserInfoDalMapper {

    UserInfo getByUserNo(String userNo);

    /**
     * 执行更改
     * */
    void execUpdateSql(
            @Param("updateSql") String updateSql,
            @Param("entityData") Map<String, Object> entityData
    );

    /**
     * 更新字段值
     * */
//    void updateColValue(
//        String tableName,
//    );
}