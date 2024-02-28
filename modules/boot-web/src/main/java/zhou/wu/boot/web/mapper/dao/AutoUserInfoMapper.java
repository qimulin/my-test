package zhou.wu.boot.web.mapper.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import zhou.wu.boot.web.domain.AutoUserInfo;
import zhou.wu.boot.web.domain.AutoUserInfoExample;

public interface AutoUserInfoMapper {
    int countByExample(AutoUserInfoExample example);

    int deleteByExample(AutoUserInfoExample example);

    int deleteByPrimaryKey(Integer userid);

    int insert(AutoUserInfo record);

    int insertSelective(AutoUserInfo record);

    List<AutoUserInfo> selectByExample(AutoUserInfoExample example);

    AutoUserInfo selectByPrimaryKey(Integer userid);

    int updateByExampleSelective(@Param("record") AutoUserInfo record, @Param("example") AutoUserInfoExample example);

    int updateByExample(@Param("record") AutoUserInfo record, @Param("example") AutoUserInfoExample example);

    int updateByPrimaryKeySelective(AutoUserInfo record);

    int updateByPrimaryKey(AutoUserInfo record);
}