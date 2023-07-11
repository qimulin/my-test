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

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.AbstractDiscriminator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task Log Discriminator
 */
public class SiftLogDiscriminator extends AbstractDiscriminator<ILoggingEvent> {

    private static Logger logger = LoggerFactory.getLogger(SiftLogDiscriminator.class);

    /**
     * my key
     */
    private String key;

    /**
     * log base
     */
    private String logBase;

    /**
     * logger name should be like:
     * Logger name should be like: allBizId-{part1Id}-{part2Id}-{part3Id}
     */
    @Override
    public String getDiscriminatingValue(ILoggingEvent event) {
        String key = "unknown_key";

        logger.info("sift log discriminator start, key is:{}, thread name:{},loggerName:{}", key, event.getThreadName(), event.getLoggerName());

        if (event.getLoggerName().startsWith(SiftConstants.SIFT_LOG_LOGGER_NAME)) {
            String threadName = event.getThreadName();
            if (threadName.endsWith(SiftConstants.GET_OUTPUT_LOG_SERVICE)) {
                threadName = threadName.substring(0, threadName.length() - SiftConstants.GET_OUTPUT_LOG_SERVICE.length());
            }
            // 取格式中的{part3Id}作为key
            // 取等号分割后的第二部分,
            String part1 = threadName
                    .split("=")[1];
            // 构建前缀，看是否是该前缀开头
            String prefix = SiftConstants.SIFT_LOGGER_INFO_PREFIX + "-";
            if (part1.startsWith(prefix)) {
                // 截取前缀后的内容
                key = part1.substring(prefix.length())
                        // 将"-"都替换成"/"，届时可以成为文件夹分层
                        .replace("-", "/");
            }
        }
        logger.info("sift log discriminator end, key is:{}, thread name:{}, loggerName:{}", key, event.getThreadName(), event.getLoggerName());
        return key;
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLogBase() {
        return logBase;
    }

    public void setLogBase(String logBase) {
        this.logBase = logBase;
    }
}
