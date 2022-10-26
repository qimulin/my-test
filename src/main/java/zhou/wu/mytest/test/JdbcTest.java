package zhou.wu.mytest.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author zhou.wu
 * @description: jdbc测试
 * @date 2022/10/26
 **/
public class JdbcTest {

    public static void main(String[] args) throws Exception{
        //要连接的数据库URL
        String url = "地址";
        //连接的数据库时使用的用户名
        String username = "用户名";
        //连接的数据库时使用的密码
        String password = "密码";

        //1.加载驱动
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());不推荐使用这种方式来加载驱动
        Class.forName("com.mysql.jdbc.Driver");//推荐使用这种方式来加载驱动
        //2.获取与数据库的链接
        Connection conn = DriverManager.getConnection(url, username, password);

        //3.获取用于向数据库发送sql语句的statement
        Statement st = conn.createStatement();

        String sql = "select * from api;";
        //4.向数据库发sql,并获取代表结果集的resultset
        ResultSet rs = st.executeQuery(sql);

        //5.取出结果集的数据
        while(rs.next()){
            System.out.println("userId=" + rs.getObject("userId"));
        }

        //6.关闭链接，释放资源
        rs.close();
        st.close();
        conn.close();
    }
}
