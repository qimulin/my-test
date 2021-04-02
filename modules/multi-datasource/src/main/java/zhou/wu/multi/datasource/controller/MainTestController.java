package zhou.wu.multi.datasource.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhou.wu.multi.datasource.domain.TabTest;
import zhou.wu.multi.datasource.domain.UserInfo;
import zhou.wu.multi.datasource.service.MainTestService;

/**
 * @author Lin.xc
 * @date 2019/10/12
 */
@Slf4j
@RestController
@RequestMapping("/main-test")
public class MainTestController {

    @Autowired
    private MainTestService mainTestService;

    @GetMapping("/find-user-info")
    public UserInfo findUserInfo(String userNo){
        // 根据userNo查询
        UserInfo info = mainTestService.findUserInfo(userNo);
        return info;
    }

    @GetMapping("/find-tab-test")
    public TabTest findTabTest(Long id){
        // 根据userNo查询
        TabTest result = mainTestService.findTabTest(id);
        return result;
    }

    @PostMapping("/exec-update-sql")
    public void execUpdateSql(){
        // 根据userNo查询
        mainTestService.updateColValue();
        log.info("更改成功！");
    }

}
