package zhou.wu.boot.spark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.mortbay.util.ajax.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

import static zhou.wu.boot.spark.Constants.*;


@SpringBootApplication
public class SparkTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(SparkTestApplication.class, args);
        SparkSession sparkSession = buildSparkSession();
        Config connectConfig = buildDbConnectConfig();
        connect(sparkSession, connectConfig);
        String tempView = "t_190_9_2";
        execute(sparkSession, "select * from (select * from api where 1=1) t where userName is null  limit 1000", tempView);
        Dataset<Row> rowDataset = queryTableData(sparkSession, tempView);
        Config writerConfig = buildHdfsWriterConfig();
        outputImpl(sparkSession, writerConfig, rowDataset, "hdfs://", tempView);
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

    private static Config buildDbConnectConfig(){
        String jsonStr = "{\"database\":\"dev_dfactory_data\",\"password\":\"Dongxq@123\",\"driver\":\"com.mysql.jdbc.Driver\",\"user\":\"dongxq\",\"table\":\"api\",\"url\":\"jdbc:mysql://rm-bp153srx1gt80tl1x2o.mysql.rds.aliyuncs.com:3306/dev_dfactory_data?allowLoadLocalInfile=false&amp;autoDeserialize=false&amp;allowLocalInfile=false&amp;allowUrlInLocalInfile=false\"}";
        ObjectMapper om = new ObjectMapper();
        Map<String,Object> properties = null;
        try {
            properties = om.readValue(jsonStr,Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Config(properties);
    }

    private static void connect(SparkSession sparkSession, Config config){
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

    private static void execute(SparkSession sparkSession, String sql, String tempView){
        sparkSession
                .sql(sql)
                .createOrReplaceTempView(tempView);
    }

    private static Dataset<Row> queryTableData(SparkSession sparkSession, String tableName){
        Dataset<Row> rowList = sparkSession.table(tableName);
        rowList.show();
//        System.out.println(JSON.toString(rowList));
        return rowList;
    }

    private static Config buildHdfsWriterConfig(){
        String jsonStr = "{\"abnormal_output_path\":\"hdfs://localhost:9000/tmp/20220525001\"}";
        ObjectMapper om = new ObjectMapper();
        Map<String,Object> properties = null;
        try {
            properties = om.readValue(jsonStr,Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Config config = new Config(properties);
        Map<String,Object> defaultConfig = new HashMap<>();

        defaultConfig.put(PARTITION_BY, Collections.emptyList());
        defaultConfig.put(SAVE_MODE,"overwrite");   // 数据开发-数据质量用error
        defaultConfig.put(SERIALIZER,"json");
        defaultConfig.put("options.nullValue","null");
        defaultConfig.put("options.emptyValue","empty");
        config.merge(defaultConfig);
        return config;
    }

    private static String outputImpl(
            SparkSession sparkSession,
            Config config,
            Dataset<Row> df,
            String defaultUriSchema,
            String fileName
    ) {
        DataFrameWriter<Row> writer = df.write().mode(config.getString(SAVE_MODE));

        if (CollectionUtils.isNotEmpty(config.getStringList(PARTITION_BY))) {
            List<String> partitionKeys = config.getStringList(PARTITION_BY);
            writer.partitionBy(partitionKeys.toArray(new String[]{}));
        }

        Config fileConfig = ConfigUtils.extractSubConfig(config, "options.", false);
        if (fileConfig.isNotEmpty()) {
            Map<String,String> optionMap = new HashMap<>(16);
            fileConfig.entrySet().forEach(x -> optionMap.put(x.getKey(),String.valueOf(x.getValue())));
            writer.options(optionMap);
        }

        StringBuffer pathSb = new StringBuffer(buildPathWithDefaultSchema(config.getString(DQ_ABNORMAL_OUTPUT_PATH), defaultUriSchema));
        String path = pathSb.append(SINGLE_SLASH).append(fileName).toString();

        switch (config.getString(SERIALIZER)) {
            case "csv":
                writer.csv(path);
                break;
            case "json":
                writer.format("json").save(path);
//                Dataset<String> stringDataset = df.toJSON();
//                DataFrameWriter<String> jsonWriter = stringDataset.write().mode(config.getString(SAVE_MODE));
//                stringDataset.show(false);
//                jsonWriter.json(path);
                break;
            case "parquet":
                writer.parquet(path);
                break;
            case "text":
                writer.text(path);
                break;
            case "orc":
                writer.orc(path);
                break;
            default:
                break;
        }

        return path;
    }

    private static final void batchInsert(SparkSession sparkSession){
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
//        sparkSession
//                .sql("select count(id) from t_ds_user")
//                .createOrReplaceTempView("temp_view");
//        String[] arr = new String[2];
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
    }

    private static String buildPathWithDefaultSchema(String uri, String defaultUriSchema) {
        return uri.startsWith(SINGLE_SLASH) ? defaultUriSchema + uri : uri;
    }

}
