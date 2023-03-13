package executor.service.service;

import executor.service.model.ProxyConfigHolderDTO;
import org.openqa.selenium.WebDriver;

public interface WebDriverInitializer {

    WebDriver create(ProxyConfigHolderDTO proxyConfigHolder);

}
