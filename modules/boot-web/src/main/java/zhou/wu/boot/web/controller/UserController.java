package zhou.wu.boot.web.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhou.wu.boot.web.domain.User;
import zhou.wu.boot.web.service.UserService;

import java.util.List;

/**
 * @author Lin.xc
 * @date 2019/10/12
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public List<User> findUser(String keyWord){
        return userService.list(Wrappers.lambdaQuery(User.class).like(User::getName, keyWord));
    }

}
