package zhou.wu.boot.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhou.wu.boot.web.annotation.DemoAnnotation;
import zhou.wu.boot.web.domain.AutoUserInfo;
import zhou.wu.boot.web.service.UserInfoService;

import java.util.List;

/**
 * @author Lin.xc
 * @date 2019/10/12
 */
@Slf4j
@RestController
@RequestMapping("/user-info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/by-user-no")
    public AutoUserInfo findUserInfo(String userNo){
        // 根据userNo查询
        AutoUserInfo autoUserInfo = userInfoService.selectByUserNo(userNo);
        return autoUserInfo;
    }

//    @GetMapping("/str")
//    public String getDemoStr(){
//        String result = "CREATE TABLE IF NOT EXISTS stg_huzmis_da_yhbk_rt (\n\t`hh` BIGINT COMMENT '',\n\t`khh` BIGINT COMMENT '',\n\t`bzmc` STRING COMMENT '',\n\t`zbwz` STRING COMMENT '',\n\t`zbwzid` BIGINT COMMENT '',\n\t`yhdz` STRING COMMENT '',\n\t`yhdzid` BIGINT COMMENT '',\n\t`yhzt` STRING COMMENT '',\n\t`csy` BIGINT COMMENT '',\n\t`yyqy` BIGINT COMMENT '',\n\t`bch` STRING COMMENT '',\n\t`slqy` STRING COMMENT '',\n\t`bwh` STRING COMMENT '',\n\t`sqrq` TIMESTAMP COMMENT '',\n\t`zysxz` STRING COMMENT '',\n\t`ysxzfaid` BIGINT COMMENT '',\n\t`qcy` BIGINT COMMENT '',\n\t`cbzq` BIGINT COMMENT '',\n\t`yhlx` STRING COMMENT '',\n\t`sbid` BIGINT COMMENT '',\n\t`bklsid` BIGINT COMMENT '',\n\t`zbhh` BIGINT COMMENT '',\n\t`lxr` STRING COMMENT '',\n\t`lxdh` STRING COMMENT '',\n\t`sjhm` STRING COMMENT '',\n\t`sfzbh` STRING COMMENT '',\n\t`ms1` STRING COMMENT '',\n\t`ms2` STRING COMMENT '',\n\t`ms3` STRING COMMENT '',\n\t`ms4` STRING COMMENT '',\n\t`bkbz1` STRING COMMENT '',\n\t`bkbz2` STRING COMMENT '',\n\t`bkbz3` STRING COMMENT '',\n\t`bkbz4` STRING COMMENT '',\n\t`zzsh` BIGINT COMMENT '',\n\t`ysrk` BIGINT COMMENT '',\n\t`kj` BIGINT COMMENT '',\n\t`gshth` STRING COMMENT '',\n\t`yhzl` STRING COMMENT '',\n\t`jlsfbz` BIGINT COMMENT '',\n\t`lhbz` BIGINT COMMENT '',\n\t`ejyhfz` BIGINT COMMENT '',\n\t`yxfjsl` BIGINT COMMENT '',\n\t`bkjid` BIGINT COMMENT '',\n\t`rowstamp` TIMESTAMP COMMENT '',\n\t`ysxzsl` BIGINT COMMENT '',\n\t`ybdd` BIGINT COMMENT '',\n\t`czrxm` STRING COMMENT '',\n\t`kpzdxz` BIGINT COMMENT '',\n\t`bkbz5` STRING COMMENT '',\n\t`bkbz6` STRING COMMENT '',\n\t`bkbz7` STRING COMMENT '',\n\t`bkbz8` STRING COMMENT '',\n\t`bkbz9` STRING COMMENT '',\n\t`bkbz10` STRING COMMENT '',\n\t`ms5` STRING COMMENT '',\n\t`ms6` STRING COMMENT '',\n\t`ms7` STRING COMMENT '',\n\t`ms8` STRING COMMENT '',\n\t`ms9` STRING COMMENT '',\n\t`ms10` STRING COMMENT '',\n\t`mbhh` BIGINT COMMENT '',\n\t`zblx` STRING COMMENT '',\n\t`ysflh` STRING COMMENT '',\n\t`sbqk` STRING COMMENT '',\n\t`qfh` STRING COMMENT ''\n)\nCOMMENT '用户表卡信息'\nSTORED AS ORC;";
//        return result;
//    }

    @GetMapping("/list")
    public List<AutoUserInfo> listUserInfo(String userNo){
        return userInfoService.listAutoUserInfo(null);
    }

    @PostMapping("/list/by-cond")
    public List<AutoUserInfo> listUserInfoByCondition(
            @RequestBody AutoUserInfo userInfo
    ){
        return userInfoService.listAutoUserInfo(userInfo);
    }

    @PostMapping("/add")
//    @DemoAnnotation(value = "insertUserInfo", primaryId = "#userInfo.userage")
    public AutoUserInfo insertUserInfo(@RequestBody AutoUserInfo userInfo){
        AutoUserInfo autoUserInfo = userInfoService.selectByUserNo(userInfo.getUserno());
//        if(autoUserInfo==null){
//            userInfoService.insertAutoUserInfo(userInfo);
//        }else{
//            log.info("无需插入！[{}]", JSON.toJSONString(userInfo));
//        }
        System.out.println("虚的插入！");
//        return Boolean.TRUE;
        return autoUserInfo;
    }

    @PutMapping
    public Boolean updateUserInfo(@RequestBody AutoUserInfo userInfo){
        userInfoService.updateAutoUserInfo(userInfo);
        return Boolean.TRUE;
    }

    /**
     * 事务测试-疑似幻读
     * */
    @GetMapping("/tx-test1/{userNo}")
    public Boolean txTest1(@PathVariable String userNo){
        return userInfoService.txTest1(userNo);
    }

    /**
     * 事务测试-脏读
     * */
    @GetMapping("/tx-test2/{userNo}")
    public Boolean txTest2(@PathVariable String userNo){
       return userInfoService.txTest2(userNo);
    }

}
