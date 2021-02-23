package zhou.wu.multi.datasource.mapper.dal;

import zhou.wu.multi.datasource.domain.UserInfo;

public interface UserInfoDalMapper {

    UserInfo getByUserNo(String userNo);
}