package executor.service.config;

import org.apache.commons.configuration2.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesLoaderTest {

    private final String testProperties = "application-test.properties";
    private static PropertiesLoader loader;
    private Configuration testConfiguration;

    @BeforeAll
    static void beforeAll() {
        loader = PropertiesLoader.getInstance();
    }

    @BeforeEach
    void setup() {
        testConfiguration = loader.loadConfiguration(testProperties);
    }

    @Test
    void shouldBeInitialized() {
        assertNotNull(loader);
    }

    @Test
    void shouldBeInSingleInstance() {
        PropertiesLoader sameInstanceLoader = PropertiesLoader.getInstance();
        assertEquals(loader, sameInstanceLoader);
    }

    @Test
    void shouldLoadNotNullConfigurationType() {
        assertNotNull(testConfiguration);
        assertInstanceOf(Configuration.class, testConfiguration);
    }

    @Test
    void shouldLoadSameInstanceOfConfigWithSameFilename() {
        Configuration anotherTestConfiguration = loader.loadConfiguration(testProperties);
        assertEquals(testConfiguration, anotherTestConfiguration);
    }

    @Test
    void shouldLoadDifferentConfigWithDifferentFilenames() {
        Configuration differentConfiguration = loader.loadConfiguration("example.properties");
        assertNotEquals(testConfiguration, differentConfiguration);
    }

    @Test
    void shouldThrowRuntimeExceptionWithWrongFilename() {
        assertThrows(RuntimeException.class, () -> loader.loadConfiguration("wrong filename"));
    }

}