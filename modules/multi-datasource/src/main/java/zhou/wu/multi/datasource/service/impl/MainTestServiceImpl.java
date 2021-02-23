package zhou.wu.multi.datasource.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhou.wu.multi.datasource.annotion.TargetDataSource;
import zhou.wu.multi.datasource.domain.TabTest;
import zhou.wu.multi.datasource.domain.UserInfo;
import zhou.wu.multi.datasource.mapper.dal.TabTestOdpsDalMapper;
import zhou.wu.multi.datasource.mapper.dal.UserInfoDalMapper;
import zhou.wu.multi.datasource.service.MainTestService;

/**
 * @author lin.xc
 * @date 2021/2/22
 **/
@Service
public class MainTestServiceImpl implements MainTestService {

    @Autowired
    private UserInfoDalMapper userInfoDalMapper;

    @Autowired
    private TabTestOdpsDalMapper tabTestOdpsDalMapper;

    @Override
    public UserInfo findUserInfo(String userNo) {
        // 根据userNo查询
        UserInfo info = userInfoDalMapper.getByUserNo(userNo);
        return info;
    }

    @Override
    @TargetDataSource
    public TabTest findTabTest(Long id) {
        TabTest result = tabTestOdpsDalMapper.getById(id);
        return result;
    }
}
