package zhou.wu.boot.web.mapper.dal;

import zhou.wu.boot.web.domain.AutoUserInfo;

public interface AutoUserInfoDalMapper {

    AutoUserInfo getByUserNo(String userNo);
}