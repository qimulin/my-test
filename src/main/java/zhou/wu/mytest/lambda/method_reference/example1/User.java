package zhou.wu.mytest.lambda.method_reference.example1;

import lombok.Data;

/**
 * @author lin.xc
 * @date 2020/9/17
 **/
@Data
public class User {

    private String username;
    private Integer age;

    public User() {
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

}
