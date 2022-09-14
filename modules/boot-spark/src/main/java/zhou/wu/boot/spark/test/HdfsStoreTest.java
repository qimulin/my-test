package zhou.wu.boot.spark.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zhou.wu.boot.spark.test.Constants.*;

/**
 * @author zhou.wu
 * @description: Hdfs存储
 * @date 2022/9/14
 **/
public class HdfsStoreTest {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkTestCommon.buildSparkSession();
        // 连接
        String tableName = "api";
        String connectJson = String.format(SparkTestCommon.CONNECT_CONFIG_JSON_STR1, tableName);
        Config connectConfig = SparkTestCommon.jsonConvertConfig(connectJson);
        SparkTestCommon.connect(sparkSession, connectConfig);
        // 查表数据，并写入HDFS
        String fileName = "20220914";
        Dataset<Row> rowDataset = SparkTestCommon.queryTableData(sparkSession, tableName);
        Config writerConfig = buildHdfsWriterConfig();
        outputImpl(sparkSession, writerConfig, rowDataset, "hdfs://", fileName);
        sparkSession.stop();
    }

    private static Config buildHdfsWriterConfig(){
        String jsonStr = "{\"abnormal_output_path\":\"hdfs://localhost:9000/tmp/20220907001\"}";
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
        defaultConfig.put(SERIALIZER,"csv");
        defaultConfig.put("options.nullValue",null);
//        defaultConfig.put("options.emptyValue","empty");
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
            fileConfig.entrySet().forEach(x -> optionMap.put(x.getKey(), x.getValue()==null?null:String.valueOf(x.getValue())));
            writer.options(optionMap);
        }

        StringBuffer pathSb = new StringBuffer(buildPathWithDefaultSchema(config.getString(DQ_ABNORMAL_OUTPUT_PATH), defaultUriSchema));
        String path = pathSb.append(SINGLE_SLASH).append(fileName).toString();

        switch (config.getString(SERIALIZER)) {
            case "csv":
                writer.option("header", "true").option("ignoreLeadingWhiteSpace", "false").option("ignoreTrailingWhiteSpace", "false").csv(path);
                break;
            case "json":
                writer.option("emptyValue", "kz").option("nullValue", "k").format("json").save(path);
//                writer.option().option()
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

    private static String buildPathWithDefaultSchema(String uri, String defaultUriSchema) {
        return uri.startsWith(SINGLE_SLASH) ? defaultUriSchema + uri : uri;
    }
}
