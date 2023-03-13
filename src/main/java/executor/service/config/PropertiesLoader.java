package executor.service.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PropertiesLoader implements ConfigLoader {

    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private static volatile PropertiesLoader instance;
    private final Configuration configuration;

    private PropertiesLoader() {
        try {
            configuration = configurationBuilder().getConfiguration();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
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
    public Configuration getConfiguration() {
        return configuration;
    }

    private FileBasedConfigurationBuilder<FileBasedConfiguration> configurationBuilder() {
        Parameters parameters = new Parameters();
        return new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(parameters.properties().setFileName(PROPERTIES_FILE_NAME));
    }

}
