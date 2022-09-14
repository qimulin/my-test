package zhou.wu.boot.spark.test;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static zhou.wu.boot.spark.test.Constants.*;

/**
 * @author zhou.wu
 * @description: 插入数据测试
 * @date 2022/9/14
 **/
public class WriteDataTest {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkTestCommon.buildSparkSession();

        // 写入数据源配置
        Config config = SparkTestCommon.jsonConvertConfig(SparkTestCommon.WRITE_CONFIG_JSON_STR1);

        // 定义结构
        List schemaFields = new ArrayList();
        schemaFields.add(DataTypes.createStructField("process_definition_id", DataTypes.IntegerType, true));
        schemaFields.add(DataTypes.createStructField("process_instance_id", DataTypes.IntegerType, true));
        schemaFields.add(DataTypes.createStructField("task_instance_id", DataTypes.IntegerType, true));
        schemaFields.add(DataTypes.createStructField("s_value", DataTypes.DoubleType, true));
        StructType schema = DataTypes.createStructType(schemaFields);

        // 提供数据
        List<Row> dataList = new ArrayList<>();
        dataList.add(RowFactory.create(1,1,1,10d));
        dataList.add(RowFactory.create(1,3,1,120d));
        dataList.add(RowFactory.create(2,2,2,20d));
        dataList.add(RowFactory.create(2,5,2,40d));

        // 写入数据
        writeRowListBySchema(sparkSession, config, schema, dataList);

        sparkSession.stop();
    }

    /**
     * 写入行列表
     * */
    private static void writeRowListBySchema(
            SparkSession sparkSession,
            Config writeConfig,
            StructType schema,
            List<Row> rowList
    ) {
        // 批量插入数据
        Properties properties = new Properties();
        properties.setProperty(DRIVER,writeConfig.getString(DRIVER));
        properties.setProperty(USER, writeConfig.getString(USER));
        properties.setProperty(PASSWORD, writeConfig.getString(PASSWORD));
        sparkSession.createDataFrame(rowList, schema)
                .write()
                .mode(writeConfig.getString(SAVE_MODE))
                .jdbc(writeConfig.getString(URL), writeConfig.getString(TABLE), properties);
    }
}
