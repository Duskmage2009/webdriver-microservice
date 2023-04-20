package executor.service.service.impl;

import executor.service.model.ScenarioDTO;
import executor.service.model.StepDTO;
import executor.service.service.ExecutorService;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ScenarioExecutorService implements ExecutorService {
    private final StepExecutionClickCss executionClickCss;
    private final StepExecutionSleep executionSleep;
    private final StepExecutionClickXpath executionClickXpath;

    public ScenarioExecutorService(StepExecutionClickCss executionClickCss, StepExecutionSleep executionSleep, StepExecutionClickXpath executionClickXpath) {
        this.executionClickCss = executionClickCss;
        this.executionSleep = executionSleep;
        this.executionClickXpath = executionClickXpath;
    }

    @Override
    public void execute(ScenarioDTO scenario, WebDriver webDriver) {
        List<StepDTO> steps = scenario.getSteps();
        String scenarioSite = scenario.getSite();
        webDriver.get(scenarioSite);
        for (StepDTO step : steps) {
            String action = step.getAction();
            switch (action) {
                case "clickCss":
                    executionClickCss.step(webDriver, step);
                    break;
                case "sleep":
                    executionSleep.step(webDriver, step);
                    break;
                case "clickXpath":
                    executionClickXpath.step(webDriver, step);
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }

}

