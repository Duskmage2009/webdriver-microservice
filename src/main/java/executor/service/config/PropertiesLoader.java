package executor.service.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PropertiesLoader implements ConfigLoader {

    private static final String PROPERTIES_NAME = "application.properties";
    private static volatile PropertiesLoader instance;
    private final Map<String, Configuration> configMap;
    public static final Configuration APPLICATION_PROPERTIES = getInstance().loadConfiguration(PROPERTIES_NAME);

    private PropertiesLoader() {
        configMap = new HashMap<>();
    }

    public static PropertiesLoader getInstance() {
        PropertiesLoader result = instance;
        if (result != null) {
            return result;
        }
        synchronized (PropertiesLoader.class) {
            if (instance == null) {
                instance = new PropertiesLoader();
            }
            return instance;
        }
    }

    @Override
    public Configuration loadConfiguration(String fileName) {
        return Optional.ofNullable(configMap.get(fileName))
                .orElseGet(() -> createConfiguration(fileName));
    }

    private Configuration createConfiguration(String fileName) {
        try {
            configMap.put(fileName, configurationBuilder(fileName).getConfiguration());
            return configMap.get(fileName);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private FileBasedConfigurationBuilder<FileBasedConfiguration> configurationBuilder(String fileName) {
        Parameters parameters = new Parameters();
        return new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(parameters.properties().setFileName(fileName));
    }

}
