package zhou.wu.boot.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zhou.wu.boot.web.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-08-26 15:29:41
* @Entity zhou.wu.boot.web.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("<script>" +
            "INSERT INTO user (id, name, age, email) VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.id}, #{item.name}, #{item.age}, #{item.email})" +
            "</foreach>" +
            "</script>")
    int insertBatch(@Param("list") List<User> userList);
}




