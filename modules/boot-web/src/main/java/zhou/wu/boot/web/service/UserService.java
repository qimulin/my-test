package zhou.wu.boot.web.service;

import zhou.wu.boot.web.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2024-08-26 15:29:41
*/
public interface UserService extends IService<User> {

    /**
     * 批量插入
     *
     * @param userList	userList
     * @return int
     * @author zhou.wu
     * @date 2024/8/26
     */
    int insertBatch(List<User> userList);
}
