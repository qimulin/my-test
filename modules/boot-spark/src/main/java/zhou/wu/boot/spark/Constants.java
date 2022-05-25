/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zhou.wu.boot.spark;

/**
 * Constants
 */
public final class Constants {

    private Constants() {
        throw new IllegalStateException("Construct Constants");
    }

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATABASE = "database";

    public static final String TABLE = "table";

    public static final String URL = "url";

    public static final String USER = "user";

    public static final String PASSWORD = "password";

    public static final String DRIVER = "driver";

    public static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";

    public static final String DEFAULT_DATABASE = "default";

    public static final String SQL = "sql";

    public static final String EMPTY = "";

    public static final String DOTS = ".";

    public static final String COMMA = ",";

    public static final String SINGLE_SLASH = "/";

    public static final String DOUBLE_SLASH = "//";

    public static final String DB_TABLE = "dbtable";

    public static final String JDBC = "jdbc";

    public static final String SAVE_MODE = "save_mode";


    public static final String TASK_INSTANCE_ID = "task_instance_id";
    public static final String RULE_ID = "rule_id";
    public static final String RULE_CATEGORY = "rule_category";
    public static final String RULE_NAME = "rule_name";
    public static final String RULE_TYPE = "rule_type";
    public static final String RULE_DIMENSION = "rule_dimension";
    public static final String CHECK_MODE = "check_mode";
    public static final String COMPARISON_MODE = "comparison_mode";
    public static final String INTENSITY = "intensity";
    public static final String THRESHOLD = "threshold";
    public static final String RULE_TEMPLATE_ID = "rule_template_id";
    public static final String CHECK_COLUMN_NAME = "check_column_name";
    public static final String FILTER_SQL = "filter_sql";
    public static final String BIZ_DATE = "biz_date";
    public static final String EXECUTE_STATUS = "execute_status";
    public static final String EXECUTE_SQL = "execute_sql";
    public static final String STATISTICS_VALUE = "statistics_value";
    public static final String EXECUTE_ERROR_MSG = "execute_error_msg";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String ABNORMAL_DATA_COUNT_SQL = "abnormal_data_count_sql";
    public static final String ABNORMAL_DATA_STATISTICS_VALUE = "abnormal_data_statistics_value";
    public static final String ABNORMAL_DATA_SQL = "abnormal_data_sql";
    public static final String ABNORMAL_DATA_OUTPUT_PATH = "abnormal_data_output_path";
    public static final String ABNORMAL_DATA_LIMIT = "abnormal_data_limit";

    public static final String PARTITION_BY = "partition_by";
    public static final String SERIALIZER = "serializer";
    public static final String DQ_ABNORMAL_OUTPUT_PATH = "abnormal_output_path";

}
