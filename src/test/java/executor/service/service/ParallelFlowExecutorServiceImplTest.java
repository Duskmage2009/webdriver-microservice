package executor.service.service;

import executor.service.model.ScenarioDTO;
import executor.service.model.ThreadPoolConfigDTO;
import executor.service.service.impl.ParallelFlowExecutorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParallelFlowExecutorServiceImplTest {

    @Mock
    private ScenarioSourceListener scenarioSourceListener;
    @Mock
    private ProxySourcesClient proxySourcesClient;
    @Mock
    private ThreadPoolExecutor threadPoolExecutor;
    @Spy
    private ThreadPoolConfigDTO threadPoolConfig = new ThreadPoolConfigDTO(2, 10000L);
    @InjectMocks
    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;


    @Test
    public void shouldSetupThreadPoolExecutorWithConfigs() {
        verify(threadPoolExecutor).setCorePoolSize(threadPoolConfig.getCorePoolSize());
        verify(threadPoolExecutor).setKeepAliveTime(threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS);
    }

    @Test
    public void shouldBreakAndShutdownIfScenarioNull() {
        when(scenarioSourceListener.getScenario()).thenReturn(null);

        parallelFlowExecutorService.runParallel();

        verifyNoInteractions(proxySourcesClient);
        verify(threadPoolExecutor, times(0)).execute(any());
        verify(threadPoolExecutor).shutdown();
    }

    @Test
    public void shouldExecuteRunnableInParallelAndBreak() {
        ScenarioDTO scenario = new ScenarioDTO();
        when(scenarioSourceListener.getScenario())
                .thenReturn(scenario)
                .thenReturn(null);

        parallelFlowExecutorService.runParallel();

        verify(proxySourcesClient).getProxy();
        verify(threadPoolExecutor).execute(any());
        verify(threadPoolExecutor).shutdown();
    }

    @Test
    public void shouldExecuteThreeRunnableAndBreak() {
        ScenarioDTO scenario = new ScenarioDTO();
        int scenarioAmount = 3;
        when(scenarioSourceListener.getScenario())
                .thenReturn(scenario)
                .thenReturn(scenario)
                .thenReturn(scenario)
                .thenReturn(null);

        parallelFlowExecutorService.runParallel();

        verify(proxySourcesClient, times(scenarioAmount)).getProxy();
        verify(threadPoolExecutor, times(scenarioAmount)).execute(any());
        verify(threadPoolExecutor).shutdown();
    }

}