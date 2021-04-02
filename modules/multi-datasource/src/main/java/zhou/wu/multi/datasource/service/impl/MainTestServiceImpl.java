package zhou.wu.multi.datasource.service.impl;

import com.aliyun.odps.Odps;
import com.aliyun.odps.Table;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhou.wu.multi.datasource.annotion.TargetDataSource;
import zhou.wu.multi.datasource.domain.TabTest;
import zhou.wu.multi.datasource.domain.UserInfo;
import zhou.wu.multi.datasource.mapper.dal.TabTestOdpsDalMapper;
import zhou.wu.multi.datasource.mapper.dal.UserInfoDalMapper;
import zhou.wu.multi.datasource.service.MainTestService;
import zhou.wu.multi.datasource.util.SpringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lin.xc
 * @date 2021/2/22
 **/
@Slf4j
@Service
public class MainTestServiceImpl implements MainTestService {

    @Autowired
    private UserInfoDalMapper userInfoDalMapper;

    @Autowired
    private TabTestOdpsDalMapper tabTestOdpsDalMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

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

    @Override
    @TargetDataSource
    public void updateTest() {
        String columnName = "userAge";
        String whereColName = "userId";
        String updateSql = String.format("update user_info set %s = #{%s} where %s = #{%s}",
                columnName, columnName, whereColName, whereColName);
        Map<String, Object> entity = new HashMap<>();
        entity.put(columnName, 100);
        entity.put(whereColName, 1);
        log.info("做更新的sql：{}", updateSql);
//        userInfoDalMapper.execUpdateSql(updateSql, entity);
        sqlSessionTemplate.update(updateSql, entity);
    }

    @Override
    @TargetDataSource
    public void updateColValue() {
        Connection connection = sqlSessionTemplate.getConnection();
        String columnName = "userAge";
        String whereColName = "userId";
        String updateSql = String.format("update user_info set %s = ? where %s = ?",
                columnName, whereColName);
        Map<String, Object> entity = new HashMap<>();
        entity.put(columnName, 100);
        entity.put(whereColName, 1);
        try {
            log.info("做更新的sql：{}", updateSql);
            PreparedStatement ps = connection.prepareStatement(updateSql);
            ps.setObject(1, 100);
            ps.setObject(2,47);
            int n = ps.executeUpdate();
            log.info("影响{}条", n);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Odps对象
     * */
    private Odps getOdps(){
       return SpringUtils.getBean(Odps.class);
    }
}
