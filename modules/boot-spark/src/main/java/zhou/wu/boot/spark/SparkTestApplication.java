package zhou.wu.boot.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@SpringBootApplication
public class SparkTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(SparkTestApplication.class, args);
        SparkSession sparkSession = buildSparkSession();
        List schemaFields = new ArrayList();
        schemaFields.add(DataTypes.createStructField("process_definition_id", DataTypes.IntegerType, true));
        schemaFields.add(DataTypes.createStructField("process_instance_id", DataTypes.IntegerType, true));
        schemaFields.add(DataTypes.createStructField("task_instance_id", DataTypes.IntegerType, true));
        schemaFields.add(DataTypes.createStructField("s_value", DataTypes.DoubleType, true));
        StructType schema = DataTypes.createStructType(schemaFields);
        List<Row> dataList = new ArrayList<>();
        dataList.add(RowFactory.create(1,1,1,10d));
        dataList.add(RowFactory.create(1,3,1,120d));
        dataList.add(RowFactory.create(2,2,2,20d));
        dataList.add(RowFactory.create(2,5,2,40d));
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "j20032004");
        properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        // 插入数据
        sparkSession.createDataFrame(dataList, schema).write().mode(SaveMode.Append)
                .jdbc("jdbc:mysql://127.0.0.1:3306/dolphinscheduler", "test_execute_result", properties);
        String sql = "select 1 AS process_definition_id, 1 AS process_instance_id, 1 AS task_instance_id";
        // 连接数据源
        sparkSession
                .read()
                .format("jdbc")
                .option("driver","com.mysql.jdbc.Driver")
                .option("url","jdbc:mysql://127.0.0.1:13306/dolphinscheduler")
                .option("dbtable", "dolphinscheduler.t_ds_user")
                .option("user", "root")
                .option("password", "j20032004")
                .load().createOrReplaceTempView("t_ds_user");
        // 查询并存入结果
        sparkSession
                .sql("select count(id) from t_ds_user")
                .createOrReplaceTempView("temp_view");
        String[] arr = new String[2];
        // 查询临时视图数据
//        Dataset<Row> queryResult = sparkSession.sql("select * from temp_view");
        Dataset<Row> queryResult = sparkSession.table("temp_view");
        Object value = queryResult.first().get(0);
//
//        JavaRDD<Row> rowJavaRDD = sparkSession.sql("select `count(id)` as statistics_value from temp_view").javaRDD();
//        JavaRDD<String> rowRDD = rowJavaRDD.map(
//                new Function<Row, String>() {
//                    public String call(Row line) throws Exception {
//
//                        return "";
//                    }
//        });
//        missResult.getRows(0,0).copyToArray(arr);
        queryResult.show();
        sparkSession.stop();
    }

    /**
     * 构建SparkSession
     * */
    private static SparkSession buildSparkSession(){
        SparkConf conf = new SparkConf().setMaster("local").setAppName("test-app-name");
        conf.set("spark.sql.crossJoin.enabled", "true");
        SparkSession sparkSession = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
        return sparkSession;
    }


}
