package executor.service.service;

import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ScenarioDTO;
import executor.service.model.ThreadPoolConfigDTO;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private final ScenarioSourceListener scenarioSourceListener;
    private final ProxySourcesClient proxySourcesClient;
    private final ExecutionService executionService;
    private final ThreadPoolExecutor threadPoolExecutor;

    public ParallelFlowExecutorServiceImpl(ScenarioSourceListener scenarioSourceListener,
                                           ProxySourcesClient proxySourcesClient,
                                           ExecutionService executionService,
                                           ThreadPoolConfigDTO threadPoolConfig,
                                           ThreadPoolExecutor threadPoolExecutor) {
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.executionService = executionService;
        this.threadPoolExecutor = threadPoolExecutor;
        threadPoolExecutor.setCorePoolSize(threadPoolConfig.getCorePoolSize());
        threadPoolExecutor.setKeepAliveTime(threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void runParallel() {
        while (true) {
            ScenarioDTO scenario = scenarioSourceListener.getScenario();
            if (scenario == null) {
                break;
            }
            executeScenarioInParallel(scenario);
        }
        threadPoolExecutor.shutdown();
    }

    private void executeScenarioInParallel(ScenarioDTO scenario) {
        ProxyConfigHolderDTO proxy = proxySourcesClient.getProxy();
        threadPoolExecutor.execute(() -> executionService.execute(scenario, proxy));
    }

}
