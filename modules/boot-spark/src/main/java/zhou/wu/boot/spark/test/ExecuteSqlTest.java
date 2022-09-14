package zhou.wu.boot.spark.test;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @author zhou.wu
 * @description: 执行SQL测试
 * @date 2022/9/14
 **/
public class ExecuteSqlTest {

    public static void main(String[] args) {
        SparkSession sparkSession = SparkTestCommon.buildSparkSession();
        // 连接
        String tableName = "idname";
        String connectJson = String.format(SparkTestCommon.CONNECT_CONFIG_JSON_STR1, tableName);
        Config connectConfig = SparkTestCommon.jsonConvertConfig(connectJson);
        SparkTestCommon.connect(sparkSession, connectConfig);
        // 执行SQL
        String sql = "select * from (select * from api where 1=1) t where userName is null limit 1000";
//        String sql = "select is_valid_id_card(userId) from (select * from api where 1=1) t";
//        String sql = "select userId,userName,user_name,email from api inner join t_ds_user on id=userid";
//        String sql = "select t1.userId,t1.userName,t2.area_pre,t2.city_pre from api as t1 inner join EMP0527 as t2 on t1.userId=t2.id";
//        String tempView = "tempView";
//        execute(sparkSession, sql, tempView);
//        // 查询生成完的数据
//        Dataset<Row> queryResult = sparkSession.table(tempView);
        Dataset<Row> queryResult = sparkSession.table(tableName);
        queryResult.show();
        sparkSession.stop();
    }

    private static void execute(SparkSession sparkSession, String sql, String tempView){
        sparkSession
                .sql(sql)
                .createOrReplaceTempView(tempView);
    }
}
