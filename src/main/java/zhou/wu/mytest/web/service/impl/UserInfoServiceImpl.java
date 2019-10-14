package zhou.wu.mytest.web.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zhou.wu.mytest.web.dao.AutoUserInfoMapper;
import zhou.wu.mytest.web.domain.AutoUserInfo;
import zhou.wu.mytest.web.service.UserInfoService;

/**
 * 自解：事务的隔离级别和Mybatis的一级缓存貌似会影响到，这个后续我还需要多研究
 * @author Lin.xc
 * @date 2019/10/14
 */
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private AutoUserInfoMapper autoUserInfoMapper;

    @Override
    public AutoUserInfo selectByUserNo(String userNo) {
        return autoUserInfoMapper.selectByUserNo(userNo);
    }

    @Override
    //    @Transactional(propagation = Propagation.NESTED)  // 嵌套事务
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)  // 新起事务
    public void insertAutoUserInfo(AutoUserInfo userInfo) {
        log.info("进新增方法");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        autoUserInfoMapper.insertSelective(userInfo);
        log.info("插入成功！[{}]", JSON.toJSONString(userInfo));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)  // 新起事务
    public void updateAutoUserInfo(AutoUserInfo userInfo) {
        log.info("进修改方法");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = autoUserInfoMapper.updateByPrimaryKey(userInfo);
        log.info("修改数据！影响条数：[{}]；数据为：[{}]", i,JSON.toJSONString(userInfo));
    }


    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)    // 隔离级别读-提交
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)  // 新起事务
//    @Transactional
    public Boolean txTest1(String userNo) {
//        try {
//            Thread.sleep(8000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        boolean flag=false;
        AutoUserInfo autoUserInfo = autoUserInfoMapper.selectByUserNo(userNo);
        if(autoUserInfo==null){
//            AutoUserInfo userInfo1=new AutoUserInfo();
//            userInfo1.setUserno(userNo);
//            insertAutoUserInfo(userInfo1);
            flag= retryConfirm(1,6,userNo);
        }
        if(flag){
            log.info("未找到编号[{}]的记录",userNo);
            AutoUserInfo userInfo=new AutoUserInfo();
            userInfo.setUserno(userNo);
            insertAutoUserInfo(userInfo);
        }else{
            log.info("找到编号[{}]的记录",userNo);
        }
        return flag;
    }

    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)    // 隔离级别读-提交
//    @Transactional
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)  // 新起事务
    public Boolean txTest2(String userNo) {
        boolean flag=false;
        AutoUserInfo autoUserInfo = autoUserInfoMapper.selectByUserNo(userNo);
        if(autoUserInfo==null){
            log.info("无找到对应记录，userNo：[{}]",userNo);
            return flag;
        }
        if("关羽".equals(autoUserInfo.getUsername())){
            log.info("已修改，userNo：[{}]",userNo);
            flag=true;
        }else{
            log.info("未修改，准备重试确认！userNo：[{}]",userNo);
            flag =retryConfirmUpdate(1,6,userNo);
        }
        return flag;
    }

    /********************************** 辅助方法 ****************************************/
    @SuppressWarnings("Duplicates")
    private boolean retryConfirm(int currNum,int maxRetryNum,String userNo){
        // 如果当前次数小于最大重试次数，则重试查询
        if(currNum<=maxRetryNum){
            log.info("本次重试确认对象是否为空，最大重试次数[{}]，当前次数[{}]",maxRetryNum,currNum);
            // 查询通道状态
            AutoUserInfo autoUserInfo = autoUserInfoMapper.selectByUserNo(userNo);
            if (autoUserInfo == null) {
//                if(currNum==3){
//                    AutoUserInfo userInfo1=new AutoUserInfo();
//                    userInfo1.setUserno(userNo);
//                    insertAutoUserInfo(userInfo1);
//                }
                // 为空，休息3s，再次重试
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return retryConfirm(currNum+1, maxRetryNum, userNo);
            }else{
                log.info("重试跳出！已找到");
                return false;
            }
        }
        log.info("重试完成！未找到");
        return true;
    }

    @SuppressWarnings("Duplicates")
    private boolean retryConfirmUpdate(int currNum,int maxRetryNum,String userNo){
        // 如果当前次数小于最大重试次数，则重试查询
        if(currNum<=maxRetryNum){
            log.info("本次重试确认对象是否被修改，最大重试次数[{}]，当前次数[{}]",maxRetryNum,currNum);
            // 查询通道状态
            AutoUserInfo autoUserInfo = autoUserInfoMapper.selectByUserNo(userNo);
            if(autoUserInfo==null){
                log.info("无找到对应记录，userNo：[{}]",userNo);
            }else{
                if("关羽".equals(autoUserInfo.getUsername())){
                    log.info("重试跳出！已修改");
                    return true;
                }else{
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return retryConfirmUpdate(currNum+1, maxRetryNum, userNo);
                }
            }
        }
        log.info("重试完成！未修改");
        return false;
    }

}
