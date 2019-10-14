package zhou.wu.mytest.web.service;

import zhou.wu.mytest.web.domain.AutoUserInfo;

/**
 * @author Lin.xc
 * @date 2019/10/14
 */
public interface UserInfoService {

    AutoUserInfo selectByUserNo(String userNo);

    void insertAutoUserInfo(AutoUserInfo userInfo);

    void updateAutoUserInfo(AutoUserInfo userInfo);

    Boolean txTest1(String userNo);

    Boolean txTest2(String userNo);
}
