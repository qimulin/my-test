package zhou.wu.multi.datasource.service.impl;

import com.aliyun.odps.Odps;
import com.aliyun.odps.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhou.wu.multi.datasource.annotion.TargetDataSource;
import zhou.wu.multi.datasource.config.DynamicDataSourceConfig;
import zhou.wu.multi.datasource.domain.TabTest;
import zhou.wu.multi.datasource.domain.UserInfo;
import zhou.wu.multi.datasource.mapper.dal.TabTestOdpsDalMapper;
import zhou.wu.multi.datasource.mapper.dal.UserInfoDalMapper;
import zhou.wu.multi.datasource.service.MainTestService;
import zhou.wu.multi.datasource.util.SpringUtils;

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
        Odps odps = getOdps();
        for (Table t : odps.tables()) {
            System.out.println(t.getName()+"："+t.getComment());
        }
        TabTest result = tabTestOdpsDalMapper.getById(id);
        return result;
    }

    /**
     * 获取Odps对象
     * */
    private Odps getOdps(){
       return SpringUtils.getBean(Odps.class);
    }
}
