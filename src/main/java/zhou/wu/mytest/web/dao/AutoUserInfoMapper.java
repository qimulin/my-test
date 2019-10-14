package zhou.wu.mytest.web.dao;

import org.apache.ibatis.annotations.Mapper;
import zhou.wu.mytest.web.domain.AutoUserInfo;

@Mapper
public interface AutoUserInfoMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(AutoUserInfo record);

    int insertSelective(AutoUserInfo record);

    AutoUserInfo selectByPrimaryKey(Integer userid);

    AutoUserInfo selectByUserNo(String userNo);

    int updateByPrimaryKeySelective(AutoUserInfo record);

    int updateByPrimaryKey(AutoUserInfo record);
}