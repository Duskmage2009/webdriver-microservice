package executor.service.service;

import executor.service.model.ScenarioDTO;
import org.openqa.selenium.WebDriver;

public interface ExecutorService {
    void execute(ScenarioDTO scenario, WebDriver webDriver);
}
