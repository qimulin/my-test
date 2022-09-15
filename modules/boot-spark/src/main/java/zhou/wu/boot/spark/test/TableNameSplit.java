package zhou.wu.boot.spark.test;

/**
 * @author zhou.wu
 * @description: 表名分割
 * @date 2022/7/26
 **/
public class TableNameSplit {

    private String attributionName;

    private String pureTableName;

    public TableNameSplit(String tableName) {
        // 分离归属名和表名
        String[] split = tableName.split("\\.");
        if(split.length>1){
            this.attributionName = split[0];
            this.pureTableName = split[1];
        }else{
            this.pureTableName = split[0];
        }
    }

    public String getAttributionName() {
        return attributionName;
    }

    public String getPureTableName() {
        return pureTableName;
    }
}
