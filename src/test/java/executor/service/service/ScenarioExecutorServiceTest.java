package executor.service.service;

import executor.service.model.ScenarioDTO;
import executor.service.model.StepDTO;
import executor.service.service.impl.ScenarioExecutorService;
import executor.service.service.impl.StepExecutionClickCss;
import executor.service.service.impl.StepExecutionClickXpath;
import executor.service.service.impl.StepExecutionSleep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScenarioExecutorServiceTest {
    @Mock
    private WebDriver webDriver;
    @Mock
    private StepExecutionClickCss stepExecutionClickCss;

    @Mock
    private StepExecutionSleep stepExecutionSleep;
    @Mock
    private StepExecutionClickXpath stepExecutionClickXpath;
    private ScenarioExecutorService service;
    private StepDTO stepDTO;
    private StepDTO stepDTO2;
    private StepDTO stepDTO3;
    private List<StepDTO> stepDTOList;
    private ScenarioDTO scenarioDTO;

    @BeforeEach
    public void before() {

        stepDTO = new StepDTO("clickCss", "body > ul > li > a");
        stepDTO2 = new StepDTO("sleep", "5000");
        stepDTO3 = new StepDTO("clickXpath", "/html/body/style");
        stepDTOList = new ArrayList<>(Arrays.asList(stepDTO3, stepDTO, stepDTO2));
        scenarioDTO = new ScenarioDTO("SomeName", "https://www.google.com.ua/", stepDTOList);
        service = new ScenarioExecutorService(stepExecutionClickCss, stepExecutionSleep, stepExecutionClickXpath);
    }


    @Test
    public void testForTryingExecutionClickCss() {
        service.execute(scenarioDTO, webDriver);
        Mockito.verify(stepExecutionClickCss).step(webDriver, stepDTO);
    }

    @Test
    public void testForTryingExecutionSleep() {
        service.execute(scenarioDTO, webDriver);
        Mockito.verify(stepExecutionSleep).step(webDriver, stepDTO2);
    }

    @Test
    public void testForTryingExecutionClickXpath() {
        service.execute(scenarioDTO, webDriver);
        Mockito.verify(stepExecutionClickXpath).step(webDriver, stepDTO3);
    }


    @Test
    public void throwExceptionIfSleepActionDoesntCorrect() {
        stepDTO = new StepDTO("slept", "5000");
        List<StepDTO> listSteps = new ArrayList<>(Arrays.asList(stepDTO, stepDTO2, stepDTO3));
        ScenarioDTO scenarioDTO = new ScenarioDTO("SomeName", "https://www.google.com.ua/", listSteps);
        assertThrows(RuntimeException.class, () -> service.execute(scenarioDTO, webDriver));
    }

}

