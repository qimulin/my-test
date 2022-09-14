package zhou.wu.boot.spark.test;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigUtils {

    private ConfigUtils() {
        throw new IllegalStateException("Construct ConfigUtils");
    }

    /**
     * Extract sub config with fixed prefix
     *
     * @param source config source
     * @param prefix config prefix
     * @param keepPrefix true if keep prefix
     */
    public static Config extractSubConfig(Config source, String prefix, boolean keepPrefix) {
        Map<String, Object> values = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : source.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();

            if (key.startsWith(prefix)) {
                if (keepPrefix) {
                    values.put(key, value);
                } else {
                    values.put(key.substring(prefix.length()), value);
                }
            }
        }

        return new Config(values);
    }
}
