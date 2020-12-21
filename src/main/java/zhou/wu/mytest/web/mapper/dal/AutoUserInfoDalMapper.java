package zhou.wu.mytest.web.mapper.dal;

import zhou.wu.mytest.web.domain.AutoUserInfo;

public interface AutoUserInfoDalMapper {

    AutoUserInfo getByUserNo(String userNo);
}