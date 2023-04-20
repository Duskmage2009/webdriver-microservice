package executor.service.factory;

import executor.service.config.PropertiesLoader;
import executor.service.model.ThreadPoolConfigDTO;
import executor.service.service.*;
import executor.service.service.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class DIFactory implements AbstractFactory{

    private static DIFactory INSTANCE;

    private DIFactory() {
    }

    public static DIFactory getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new DIFactory();
        }
        return INSTANCE;
    }
    java.util.concurrent.ExecutorService fixedPool = Executors.newFixedThreadPool(1);
    private ThreadPoolConfigDTO threadPoolConfigDTO = new ThreadPoolConfigDTO(PropertiesLoader.APPLICATION_PROPERTIES.getInt( "thread-pool.config.core-pool-size"),
            PropertiesLoader.APPLICATION_PROPERTIES.getLong("thread-pool.config.keep-alive-time"));
    private static final Map<Class,Object> classObjectHashMap = new HashMap<>();

    @Override
    public <T> T create(Class<T> clazz) {
        if(StepExecutionClickCss.class.isAssignableFrom(clazz)){
            Object object = classObjectHashMap.get(clazz);
            if(object == null){
                StepExecution clickCss = new StepExecutionClickCss();
                classObjectHashMap.put(clazz, clickCss);
                return (T)(clickCss);
            }
            return (T)(object);
        }

        if(StepExecutionSleep.class.isAssignableFrom(clazz)){
            Object object = classObjectHashMap.get(clazz);
            if(object == null){
                StepExecution sleep = new StepExecutionSleep();
                classObjectHashMap.put(clazz,sleep);
                return (T)sleep;
            }
            return (T)(object);
        }

        if(StepExecutionClickXpath.class.isAssignableFrom(clazz)){
            Object object = classObjectHashMap.get(clazz);
            if(object == null){
                StepExecution clickXpath = new StepExecutionClickXpath();
                classObjectHashMap.put(clazz, clickXpath);
                return (T)(clickXpath);
            }
            return (T)(object);
        }



        if(ExecutorService.class.isAssignableFrom(clazz)){
            Object object = classObjectHashMap.get(clazz);
            if (object == null) {
                ExecutorService scenarioExecutorService = new ScenarioExecutorService(create(StepExecutionClickCss.class),
                        create(StepExecutionSleep.class),
                        create(StepExecutionClickXpath.class));
                classObjectHashMap.put(clazz,scenarioExecutorService);
                return (T)(scenarioExecutorService);
            }
            return (T)(object);
        }

        if(WebDriverInitializer.class.isAssignableFrom(clazz)){
            Object object = classObjectHashMap.get(clazz);
            if (object == null) {
                WebDriverInitializer chromeDriverInitializer = new ChromeDriverInitializer(PropertiesLoader.APPLICATION_PROPERTIES);
                classObjectHashMap.put(clazz,chromeDriverInitializer);
                return (T)(chromeDriverInitializer);
            }
            return (T)(object);
        }

        if(ProxySourcesClientImpl.class.isAssignableFrom(clazz)) {
            Object object = classObjectHashMap.get(clazz);
            if (object == null) {
                ProxySourcesClient proxySourcesClientImpl = new ProxySourcesClientImpl();
                classObjectHashMap.put(clazz, proxySourcesClientImpl);
                return (T) (proxySourcesClientImpl);
            }
            return (T) (object);
        }

            if(ScenarioSourceListener.class.isAssignableFrom(clazz)){
                Object object = classObjectHashMap.get(clazz);
                if (object == null) {
                    ScenarioSourceListener scenarioSourceListenerImpl = new ScenarioSourceListenerImpl();
                    classObjectHashMap.put(clazz,scenarioSourceListenerImpl);
                    return (T)(scenarioSourceListenerImpl);
                }
                return (T)(object);
            }

            if(ParallelFlowExecutorService.class.isAssignableFrom(clazz)){
                Object object = classObjectHashMap.get(clazz);
                if (object == null) {
                    ParallelFlowExecutorService parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(create(ScenarioSourceListener.class),
                            create(ProxySourcesClientImpl.class),
                            create(ExecutionService.class),
                            threadPoolConfigDTO,
                            (ThreadPoolExecutor) fixedPool);
                    classObjectHashMap.put(clazz,parallelFlowExecutorService);
                    return (T)(parallelFlowExecutorService);
                }
                return (T)(object);
            }

            if(ExecutionService.class.isAssignableFrom(clazz)){
                Object object = classObjectHashMap.get(clazz);
                if(object == null){
                    ExecutionService executionServiceFacade = new ExecutionServiceFacade(create(WebDriverInitializer.class),
                            create(ScenarioExecutorService.class));
                    classObjectHashMap.put(clazz,executionServiceFacade);
                    return (T)(executionServiceFacade);
                }
                return (T)(object);
            }
        return null;
    }
}
