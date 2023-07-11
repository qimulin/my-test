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

package zhou.wu.boot_xxl_job.sift_log;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.commons.lang.StringUtils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * sensitive data log converter
 */
public class SensitiveDataConverter extends MessageConverter {

    /**
     * dataSource sensitive param
     */
    public static final String DATASOURCE_PASSWORD_REGEX_ORIGINAL = "(?<=((?i)password((\\\\\":\\\\\")|(=')))).*?(?=((\\\\\")|(')))";
    // 感觉上面原始的正则并不能匹配多层json的密码字段值（双引号前可能多斜杠），下面写了个最多内3层json的日志密码加密（无限的匹配耗费性能） by zhou.wu 2022/8/11
    public static final String DATASOURCE_PASSWORD_REGEX = "(?<=((?i)password((\\\\{0,3}\":\\\\{0,3}\")|(=')))).*?(?=((\\\\{0,3}\")|(')))";

    /**
     * password pattern
     */
    private final Pattern pwdPattern = Pattern.compile(DATASOURCE_PASSWORD_REGEX);


    @Override
    public String convert(ILoggingEvent event) {

        // get original log
        String requestLogMsg = event.getFormattedMessage();

        // desensitization log
        return convertMsg(requestLogMsg);
    }

    /**
     * deal with sensitive log
     *
     * @param oriLogMsg original log
     */
    private String convertMsg(final String oriLogMsg) {

        String tempLogMsg = oriLogMsg;

        if (!StringUtils.isEmpty(tempLogMsg)) {
            tempLogMsg = passwordHandler(pwdPattern, tempLogMsg);
        }
        return tempLogMsg;
    }

    /**
     * password regex
     *
     * @param logMsg original log
     */
    static String passwordHandler(Pattern pwdPattern, String logMsg) {

        Matcher matcher = pwdPattern.matcher(logMsg);

        StringBuffer sb = new StringBuffer(logMsg.length());

        while (matcher.find()) {

            String password = matcher.group();

            String maskPassword = StringUtils.repeat("*", StringUtils.length(password));

            matcher.appendReplacement(sb, maskPassword);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }


}
