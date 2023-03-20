package executor.service.config;

import org.apache.commons.configuration2.Configuration;

public interface ConfigLoader {

    Configuration loadConfiguration(String fileName);

}
