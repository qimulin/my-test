package zhou.wu.mybatisgenerator.dao;

import zhou.wu.mybatisgenerator.pojo.HiveLocation;

public interface HiveLocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HiveLocation record);

    int insertSelective(HiveLocation record);

    HiveLocation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HiveLocation record);

    int updateByPrimaryKey(HiveLocation record);
}