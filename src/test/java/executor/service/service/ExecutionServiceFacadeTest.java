package executor.service.service;

import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import executor.service.model.ScenarioDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

@ExtendWith(MockitoExtension.class)
public class ExecutionServiceFacadeTest {
    @Mock
    private ScenarioExecutorService scenarioExecutorService;
    @Mock
    private WebDriverInitializer webDriverInitializer;
    @Mock
    private ProxyConfigHolderDTO proxyConfigHolderDTO;
    @Mock
    private WebDriver webDriver;
    @Mock
    private ProxyCredentialsDTO proxyCredentialsDTO;
    @Mock
    private ProxyNetworkConfigDTO proxyNetworkConfigDTO;
    @Mock
    private ScenarioDTO scenarioDTO;

    @Test
    public void testFacadeExecute() {
        ExecutionServiceFacade executionServiceFacade = new ExecutionServiceFacade(webDriverInitializer, scenarioExecutorService);
        proxyNetworkConfigDTO = new ProxyNetworkConfigDTO();
        proxyCredentialsDTO = new ProxyCredentialsDTO();
        proxyConfigHolderDTO = new ProxyConfigHolderDTO(proxyNetworkConfigDTO, proxyCredentialsDTO);
        Mockito.when(webDriverInitializer.create(proxyConfigHolderDTO)).thenReturn(webDriver);
        executionServiceFacade.execute(scenarioDTO, proxyConfigHolderDTO);
        Mockito.verify(webDriverInitializer).create(proxyConfigHolderDTO);
        Mockito.verify(scenarioExecutorService).execute(scenarioDTO, webDriver);
        Mockito.verify(webDriver).quit();
    }
}