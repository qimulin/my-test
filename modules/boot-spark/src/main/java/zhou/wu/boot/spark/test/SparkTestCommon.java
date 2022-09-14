package zhou.wu.boot.spark.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

import static zhou.wu.boot.spark.test.Constants.*;

/**
 * @author zhou.wu
 * @description: SparkSession共同
 * @date 2022/9/14
 **/
public class SparkTestCommon {

    public static final String CONNECT_CONFIG_JSON_STR1 = "不能说的秘密";

    public static final String WRITE_CONFIG_JSON_STR1 = "不能说的秘密";

    /**
     * 构建SparkSession
     * */
    public static SparkSession buildSparkSession(){
        SparkConf conf = new SparkConf().setMaster("local").setAppName("test-app-name");
        conf.set("spark.sql.crossJoin.enabled", "true");
        /* 在 Spark 2.3 以后, Spark 增加 spark.sql.hive.convertMetastoreOrc 参数, 设定 ORC 文件使用采用向量化 Reader,
        但是会引申由于 Spark SQL 无法与 Hive SQL 采用同一 SerDe 从而对 Parquet/Hive 数据表产生处理上的不同, 因此需要限定如下参数从而让 Spark 使用 Hive 的SerDe.*/
        conf.set("spark.sql.hive.convertMetastoreOrc", "false");
        conf.set("spark.sql.hive.convertMetastoreParquet", "false");
        SparkSession sparkSession = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
        return sparkSession;
    }

    /**
     * 查询表数据
     * */
    public static Dataset<Row> queryTableData(SparkSession sparkSession, String tableName){
        Dataset<Row> rowList = sparkSession.table(tableName);
        rowList.show();
//        System.out.println(JSON.toString(rowList));
        return rowList;
    }

    /**
     * 转换配置
     * */
    public static Config jsonConvertConfig(String connectJson){
        ObjectMapper om = new ObjectMapper();
        Map<String,Object> properties = null;
        try {
            properties = om.readValue(connectJson,Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Config(properties);
    }

    /**
     * 连接
     * */
    public static void connect(SparkSession sparkSession, Config config){
        sparkSession
                .read()
                .format(JDBC)
                .option(DRIVER,config.getString(DRIVER))
                .option(URL,config.getString(URL))
                .option(DB_TABLE, config.getString(DATABASE) + DOTS + config.getString(TABLE))
                .option(USER, config.getString(USER))
                .option(PASSWORD, config.getString(PASSWORD))
                .load().createOrReplaceTempView(config.getString(TABLE));
    }

    private static void connect2(SparkSession sparkSession, Config config){
        sparkSession
                .read()
                .format(JDBC)
                .option(DRIVER,config.getString(DRIVER))
                .option(URL,config.getString(URL))
                .option(DB_TABLE, config.getString(TABLE))
                .option(USER, config.getString(USER))
                .option(PASSWORD, config.getString(PASSWORD))
                .load().createOrReplaceTempView(config.getString(TABLE));
    }

}
