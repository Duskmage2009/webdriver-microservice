package executor.service.service.impl;

import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ScenarioDTO;
import executor.service.service.ExecutionService;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

public class ExecutionServiceFacade implements ExecutionService {

    private final WebDriverInitializer webDriverInitializer;
    private final ScenarioExecutorService scenarioExecutorService;

    public ExecutionServiceFacade(WebDriverInitializer webDriverInitializer, ScenarioExecutorService scenarioExecutorService) {
        this.webDriverInitializer = webDriverInitializer;
        this.scenarioExecutorService = scenarioExecutorService;
    }

    @Override
    public void execute(ScenarioDTO scenario, ProxyConfigHolderDTO proxyConfigHolderDTO) {
        WebDriver webDriver = webDriverInitializer.create(proxyConfigHolderDTO);
        scenarioExecutorService.execute(scenario, webDriver);
        webDriver.quit();
    }

}