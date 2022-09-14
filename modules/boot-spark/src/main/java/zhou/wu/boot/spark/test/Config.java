package zhou.wu.boot.spark.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Config
 */
public class Config {

    private Map<String,Object> configuration = new HashMap<>();

    public Config() {

    }

    public Config(Map<String,Object> configuration) {
        if (configuration != null) {
            this.configuration = configuration;
        }
    }

    public String getString(String key) {
        return configuration.get(key) == null ? null : String.valueOf(configuration.get(key));
    }

    public List<String> getStringList(String key) {
        return (List<String>)configuration.get(key);
    }

    public Integer getInt(String key) {
        return Integer.valueOf(String.valueOf(configuration.get(key)));
    }

    public Boolean getBoolean(String key) {
        return Boolean.valueOf(String.valueOf(configuration.get(key)));
    }

    public Double getDouble(String key) {
        return Double.valueOf(String.valueOf(configuration.get(key)));
    }

    public Long getLong(String key) {
        return Long.valueOf(String.valueOf(configuration.get(key)));
    }

    public Boolean has(String key) {
        return configuration.get(key) != null;
    }

    public Set<Entry<String, Object>> entrySet() {
        return configuration.entrySet();
    }

    public boolean isEmpty() {
        return configuration.size() <= 0;
    }

    public boolean isNotEmpty() {
        return configuration.size() > 0;
    }

    public void put(String key, Object value) {
        this.configuration.put(key,value);
    }

    public void merge(Map<String, Object> configuration) {
        configuration.forEach(this.configuration::putIfAbsent);
    }

    public Map<String, Object> configurationMap() {
        return this.configuration;
    }
}
