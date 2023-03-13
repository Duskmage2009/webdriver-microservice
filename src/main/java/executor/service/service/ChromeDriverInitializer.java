package executor.service.service;

import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import executor.service.config.PropertiesLoader;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class ChromeDriverInitializer implements WebDriverInitializer {
    private static final Configuration CONFIGURATION = PropertiesLoader.getInstance().getConfiguration();

    static {
        System.setProperty("webdriver.chrome.driver", CONFIGURATION.getString("webdriver.config.webdriver-executable"));
    }

    @Override
    public WebDriver create(ProxyConfigHolderDTO proxyConfig) {
        ChromeOptions options = createOptions(proxyConfig);
        return new ChromeDriver(options);
    }

    private ChromeOptions createOptions(ProxyConfigHolderDTO proxyConfig) {
        ProxyCredentialsDTO proxyCredentials = proxyConfig.getProxyCredentials();
        ProxyNetworkConfigDTO proxyNetworkConfig = proxyConfig.getProxyNetworkConfig();
        long pageLoadTimeout = CONFIGURATION.getLong("webdriver.config.page-load-timeout");
        long implicitlyWait = CONFIGURATION.getLong("webdriver.config.implicitly-wait");
        String userAgent = CONFIGURATION.getString("webdriver.config.user-agent");

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                String.format("--proxy-server=%s:%s@%s:%d",
                        proxyCredentials.getUser(),
                        proxyCredentials.getPassword(),
                        proxyNetworkConfig.getHostname(),
                        proxyNetworkConfig.getPort()),
                String.format("--user-agent=%s", userAgent)
        );
        options.setPageLoadTimeout(Duration.ofMillis(pageLoadTimeout));
        options.setImplicitWaitTimeout(Duration.ofMillis(implicitlyWait));

        return options;
    }

}
