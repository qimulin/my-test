package zhou.wu.boot.web.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.query.FluxTable;

import java.util.List;

/**
 * @author zhou.wu
 * @description: influxdb客户端示例
 * @date 2022/7/21
 **/
public class InfluxDB18Example {
    public static void main(final String[] args) {

        String database = "mydb";
        String retentionPolicy = null; // autogen

//        InfluxDBClient client = InfluxDBClientFactory.create(
//                "http://test-node3:8086",
//                "root",
//                "root@123".toCharArray());

        InfluxDBClient client = InfluxDBClientFactory.createV1("http://test-node3:8086",
                "root",
                "root@123".toCharArray(),
                database,
                retentionPolicy);

//        System.out.println("*** Write Points ***");
//
//        try (WriteApi writeApi = client.makeWriteApi()) {
//
//            Point point = Point.measurement("mem")
//                    .addTag("host", "host1")
//                    .addField("used_percent", 29.43234543);
//
//            System.out.println(point.toLineProtocol());
//
//            writeApi.writePoint(point);
//        }

        System.out.println("*** Query Points ***");
        String query = String.format("from(bucket: \"%s/%s\") |> range(start: -24h)", database, retentionPolicy);

        List<FluxTable> tables = client.getQueryApi().query(query);
        tables.get(0).getRecords()
                .forEach(record -> System.out.println(String.format("%s %s: %s %s",
                        record.getTime(), record.getMeasurement(), record.getField(), record.getValue())));

        client.close();
    }
}
