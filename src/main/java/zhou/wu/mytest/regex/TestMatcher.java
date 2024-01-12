package zhou.wu.mytest.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试匹配类
 * @author zhou.wu
 * @date 2023/9/18
 * 在Java中，Matcher类提供了一组方法用于获取与正则表达式匹配的结果。以下是一些常用的Matcher类相关方法的说明：
 *
 * boolean find(): 尝试在输入字符串中查找与正则表达式匹配的下一个子序列。如果找到匹配项，则返回true，否则返回false。
 * String group(): 返回与上一次匹配操作所匹配的字符串。
 * String group(int group): 返回与指定组匹配的字符串。组是正则表达式中用括号()括起来的部分，从1开始编号。
 * int start(): 返回上一次匹配操作的匹配结果的起始索引。
 * int end(): 返回上一次匹配操作的匹配结果的结束索引。
 * 这些方法允许您检索与正则表达式匹配的字符串及其位置信息。
 *
 * 在上面示例代码中，我们使用了Matcher对象的find()方法来查找下一个匹配项，然后使用group(0)方法获取整个匹配的字符串。如果正则表达式中包含组，我们可以使用group()或group(int group)方法获取指定组的匹配字符串。
 *
 * 例如，matcher.group(0)返回与整个模式匹配的字符串，matcher.group(1)返回与第一个组匹配的字符串。
 *
 * start()和end()方法返回匹配结果的起始位置和结束位置。这些方法对于确定匹配的位置信息非常有用。
 *
 * 请注意，find()方法使用循环来查找所有匹配项。每次调用find()都会查找下一个匹配项，直到没有更多匹配项为止。
 **/
public class TestMatcher {

    public static void main(String[] args) {
        //源字符串，有http的url，有邮箱，有手机号(瞎编的，别发信息)
        String sourceStr = "http://www.baidu.com http://www.sina.com 1147391211@qq.com http://www.tencent.com 18521716520";

        //正则表达式为：
        Pattern compile = Pattern.compile("http://([a-z]+\\.)+[a-z]+");
        Matcher matcher = compile.matcher(sourceStr);
        matcher.find();
        System.out.println(matcher.group());
        System.out.println(matcher.groupCount());
        System.out.println(matcher.group(1));
//        test1();
//        test2();
//        test3();
        test4();
    }

    public static void test1(){
        // 达梦建表语句示例
        String dmCreateTableSql = "create TABLE \"WZETESTX\".\"DM9\"\r\n(\r\n\"name\" VARCHAR(20),\r\n\"age\" INT,\r\n\"STORAGE\" VARCHAR(50)) STORAGE(ON \"WZETESTX\", CLUSTERBTR) ;";

        String pattern = "((?i)^CREATE\\s+TABLE\\s+[^;]+\\(.*\\))\\s+\\bSTORAGE.*;?";
//        String pattern = "(^CREATE\\s+TABLE\\s+[^;]+\\([^;]+\\))\\s?(STORAGE.*)?;?";

//        String pattern = "^create table(?!.*;)(?!.*\\bSTORAGE\\b).*";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(dmCreateTableSql);
        // 判断是否有匹配的部分
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            // 只要建表语句表结构那一块
            String result = matcher.group(1);
            System.out.println(result);
        } else {
            System.out.println("No match found.");
        }
    }

    public static void test2(){
        String input = "This is a test string.";
        // 负向前瞻（Negative Lookahead）是正则表达式中的一种特殊语法，用于在匹配过程中指定一个条件，这个条件必须在当前位置的右侧不出现。这个“右侧”很重要
        String pattern = "(?!.*is).*";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);

        if (matcher.find()) {
            String result = matcher.group(0);
            System.out.println(result);
        } else {
            System.out.println("No match found.");
        }

    }

    public static void test3(){
        // mysql建表语句示例
        String mysqlCreateTableSql = "CREATE TABLE `t_ds_metadata_log` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                "  `metadata_instance_id` int(11) DEFAULT NULL COMMENT '元数据实例主键',\n" +
                "  `table_name` varchar(255) NOT NULL COMMENT '表名',\n" +
                "  `table_alias_name` varchar(255) DEFAULT NULL COMMENT '表注释',\n" +
                "  `version` int(11) NOT NULL COMMENT ') ',\n" +
                "  `create_time` datetime NOT NULL COMMENT 'COMMENT=',\n" +
                "  PRIMARY KEY (`id`) USING BTREE,\n" +
                "  UNIQUE KEY `idx_log` (`metadata_instance_id`,`version`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=7474 DEFAULT CHARSET=utf8";

        System.out.println(extractPureStructureCreateTableSql(mysqlCreateTableSql));

//        String pattern = "((?i)^CREATE\\s+TABLE\\s+[^;]+\\([^;]+\\)).*?((?i)\\bCOMMENT='[^']*');?";   // \s+\b(ENGINE=InnoDB).*(\bCOMMENT='.*')?;?
//        String pattern = "(^CREATE\\s+TABLE\\s+[^;]+\\([^;]+\\))\\s?(STORAGE.*)?;?";

//        String pattern = "^create table(?!.*;)(?!.*\\bSTORAGE\\b).*";
//        Pattern regex = Pattern.compile(pattern);
//        Matcher matcher = regex.matcher(mysqlCreateTableSql);
//        System.out.println("----------");
//        // 判断是否有匹配的部分
//        if (matcher.find()) {
//            int count = matcher.groupCount();
//            System.out.println(count);
//            for (int i = 1; i <= count; i++) {
//                System.out.println(i+" --- group");
//                System.out.println(matcher.group(i));
//            }
//        } else {
//            System.out.println("No match found.");
//        }

        String pattern = "CREATE TABLE `t1` \\(((?:[^)]|\\([^)]+\\))*)(?:.*?(COMMENT='[^']*'))?";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(mysqlCreateTableSql);

        if (matcher.find()) {
            String tableDefinition = matcher.group(1);
            System.out.println("Table Definition:\n" + tableDefinition);

            String commentSQL = matcher.group(2);
            if (commentSQL != null) {
                System.out.println("Comment SQL: " + commentSQL);
            }
        } else {
            System.out.println("No match found.");
        }
    }

    public static void test4(){
        // 达梦建表语句示例
        String dmCreateTableSql = "create TABLE \"WZETESTX\".\"DM9\"\r\n(\r\n\"name\" VARCHAR(20) comment ');',\r\n\"age\" INT,\r\n\"STORAGE\" VARCHAR(50)) STORAGE(ON \"WZETESTX\", CLUSTERBTR) ;";

//        String pattern = "(?i)\\s+\\bCREATE\\s+TABLE\\b\\s+(\\bIF\\s+NOT\\s+EXISTS\\b\\s+)?(\\S)\\s*\\(.*\\)\\s*.*;?";
        String pattern = "(?i)\\s*\\bCREATE\\s+TABLE\\b\\s+(\\bIF\\s+NOT\\s+EXISTS\\b\\s+)?(\\S+)\\s*\\((?s).+\\).*;?";
//        String pattern = "(^CREATE\\s+TABLE\\s+[^;]+\\([^;]+\\))\\s?(STORAGE.*)?;?";;
//        String pattern = "(^CREATE\\s+TABLE\\s+[^;]+\\([^;]+\\))\\s?(STORAGE.*)?;?";

//        String pattern = "^create table(?!.*;)(?!.*\\bSTORAGE\\b).*";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(dmCreateTableSql);
         // 判断是否有匹配的部分
        if (matcher.find()) {
            int count = matcher.groupCount();
            System.out.println(count);
            for (int i = 0; i <= count; i++) {
                System.out.println(i+" --- group");
                System.out.println(matcher.group(i));
            }
        } else {
            System.out.println("No match found.");
        }
    }

    public static String extractPureStructureCreateTableSql(String createTableSql) {
        if(createTableSql==null || createTableSql.length()==0){
            return "";
        }
        int index = createTableSql.lastIndexOf("ENGINE=");
        if(index<=0){
            return createTableSql;
        }
        String str1 = createTableSql.substring(0, index);
        String str2 = createTableSql.substring(index);
        int tableCommentIndex = str2.lastIndexOf("COMMENT");
        if(tableCommentIndex<0){
            return str1;
        }
        // 有表注释的话拼上表注释
        return str1.concat(str2.substring(tableCommentIndex));
    }
}
