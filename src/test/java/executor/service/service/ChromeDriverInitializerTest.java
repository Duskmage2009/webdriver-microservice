package executor.service.service;

import executor.service.config.PropertiesLoader;
import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import org.apache.commons.configuration2.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static executor.service.config.PropertiesLoader.APPLICATION_PROPERTIES;
import static org.junit.jupiter.api.Assertions.*;

class ChromeDriverInitializerTest {

    private WebDriverInitializer driverInitializer;
    private Configuration testConfiguration;
    private ProxyConfigHolderDTO proxyConfigHolder;
    private WebDriver webDriver;

    @BeforeEach
    public void setup() {
        proxyConfigHolder = new ProxyConfigHolderDTO(
                new ProxyNetworkConfigDTO("hostname", 80),
                new ProxyCredentialsDTO("user", "password")
        );
        testConfiguration = PropertiesLoader.getInstance().loadConfiguration("application-test.properties");
        driverInitializer = new ChromeDriverInitializer(testConfiguration);
    }

    @Test
    public void shouldBeInitialized() {
        assertNotNull(driverInitializer);
    }

    @Test
    public void shouldPreloadExpectedSystemProperty(){
        String actualProperty = System.getProperty("webdriver.chrome.driver");
        String expectedProperty = APPLICATION_PROPERTIES.getString("webdriver.config.webdriver-executable");

        assertAll(
                () -> assertNotNull(actualProperty),
                () -> assertEquals(actualProperty, expectedProperty)
        );
    }


    @Test
    @DisabledIf("webdriverExecutableUnavailable")
    public void shouldCreateNotNullObject(){
        webDriver = driverInitializer.create(proxyConfigHolder);
        assertNotNull(webDriver);
    }

    @Test
    @DisabledIf("webdriverExecutableUnavailable")
    public void shouldCreateInstanceOfChromeDriver() {
        webDriver = driverInitializer.create(proxyConfigHolder);
        assertInstanceOf(ChromeDriver.class, webDriver);
    }

    @Test
    @DisabledIf("webdriverExecutableUnavailable")
    public void shouldSetupDriverWithExpectedTimeouts() {
        webDriver = driverInitializer.create(proxyConfigHolder);
        WebDriver.Timeouts timeouts = webDriver.manage().timeouts();

        long expectedPageLoad = testConfiguration.getLong("webdriver.config.page-load-timeout");
        long expectedImplicitWait = testConfiguration.getLong("webdriver.config.implicitly-wait");
        long actualPageLoad = timeouts.getPageLoadTimeout().toMillis();
        long actualImplicitWait = timeouts.getImplicitWaitTimeout().toMillis();

        assertAll(
                () -> assertEquals(expectedPageLoad, actualPageLoad),
                () -> assertEquals(expectedImplicitWait, actualImplicitWait)
        );
    }

    @Test
    @DisabledIf("webdriverExecutableUnavailable")
    public void shouldSetupDriverWithExpectedProxyConfigs() {
        webDriver = driverInitializer.create(proxyConfigHolder);
        ChromeDriver chromeDriver = (ChromeDriver) webDriver;
        Proxy proxy = (Proxy) chromeDriver.getCapabilities().getCapability("proxy");

        String expectedProxy = createExpectedProxyValue(proxyConfigHolder);
        String actualProxy = proxy.getHttpProxy();

        assertEquals(expectedProxy, actualProxy);
    }

    @Test
    public void shouldThrowNullPointerWithNullProxyConfigHolder() {
        assertThrows(NullPointerException.class, () -> driverInitializer.create(null));
    }

    @AfterEach
    public void afterEach() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private String createExpectedProxyValue(ProxyConfigHolderDTO proxyHolder) {
        ProxyCredentialsDTO proxyCredentials = proxyHolder.getProxyCredentials();
        ProxyNetworkConfigDTO proxyNetworkConfig = proxyHolder.getProxyNetworkConfig();

        return String.format("%s:%s@%s:%d",
                proxyCredentials.getUser(),
                proxyCredentials.getPassword(),
                proxyNetworkConfig.getHostname(),
                proxyNetworkConfig.getPort());
    }

    private boolean webdriverExecutableUnavailable() {
        return APPLICATION_PROPERTIES.getBoolean("webdriver.executable.unavailable");
    }

}