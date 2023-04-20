package executor.service;

import executor.service.factory.DIFactory;
import executor.service.service.ParallelFlowExecutorService;

public class App {
    public static void main(String[] args) {
        DIFactory factory = DIFactory.getInstance();
        ParallelFlowExecutorService parallelExecutor = factory.create(ParallelFlowExecutorService.class);
        parallelExecutor.runParallel();
    }
}
