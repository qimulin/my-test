package zhou.wu.multi.datasource.service;

import zhou.wu.multi.datasource.domain.TabTest;
import zhou.wu.multi.datasource.domain.UserInfo;

/**
 * @author lin.xc
 * @date 2021/2/22
 **/
public interface MainTestService {

    UserInfo findUserInfo(String userNo);

    TabTest findTabTest(Long id);

}
