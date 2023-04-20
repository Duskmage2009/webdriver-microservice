package executor.service.service.impl;

import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import executor.service.config.PropertiesLoader;
import executor.service.service.WebDriverInitializer;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class ChromeDriverInitializer implements WebDriverInitializer {

    static {
        System.setProperty("webdriver.chrome.driver",
                PropertiesLoader.APPLICATION_PROPERTIES.getString("webdriver.config.webdriver-executable"));
    }

    private final Configuration configuration;

    public ChromeDriverInitializer(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public WebDriver create(ProxyConfigHolderDTO proxyConfig) {
        Proxy proxy = createProxy(proxyConfig);
        ChromeOptions options = createOptions(proxy);
        return new ChromeDriver(options);
    }

    private ChromeOptions createOptions(Proxy proxy) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                String.format("--user-agent=%s", configuration.getString("webdriver.config.user-agent"))
        );
        options.setProxy(proxy);
        options.setPageLoadTimeout(
                Duration.ofMillis(configuration.getLong("webdriver.config.page-load-timeout"))
        );
        options.setImplicitWaitTimeout(
                Duration.ofMillis(configuration.getLong("webdriver.config.implicitly-wait"))
        );

        return options;
    }

    private Proxy createProxy(ProxyConfigHolderDTO proxyConfig) {
        ProxyCredentialsDTO proxyCredentials = proxyConfig.getProxyCredentials();
        ProxyNetworkConfigDTO proxyNetworkConfig = proxyConfig.getProxyNetworkConfig();

        Proxy proxy = new Proxy();
        proxy.setHttpProxy(String.format("%s:%s@%s:%d",
                proxyCredentials.getUser(),
                proxyCredentials.getPassword(),
                proxyNetworkConfig.getHostname(),
                proxyNetworkConfig.getPort())
        );

        return proxy;
    }

}
