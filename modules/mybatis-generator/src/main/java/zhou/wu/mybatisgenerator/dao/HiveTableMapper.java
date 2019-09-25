package zhou.wu.mybatisgenerator.dao;

import zhou.wu.mybatisgenerator.pojo.HiveTable;

public interface HiveTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HiveTable record);

    int insertSelective(HiveTable record);

    HiveTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HiveTable record);

    int updateByPrimaryKey(HiveTable record);
}