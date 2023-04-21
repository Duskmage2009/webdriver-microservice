package executor.service.factory;

import executor.service.service.ExecutorService;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.impl.ParallelFlowExecutorServiceImpl;
import executor.service.service.impl.ScenarioExecutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DIFactoryTest {


    private AbstractFactory abstractFactory;


    @BeforeEach
    public void setUp() {
        abstractFactory = DIFactory.getInstance();
    }

    @Test
    public void createTest1() {
        assertNull(abstractFactory.create(Object.class));
    }

    @Test
    public void createTest2() {

        ExecutorService executorService = abstractFactory.create(ExecutorService.class);

        assertEquals(ScenarioExecutorService.class,executorService.getClass());
    }

    @Test
    public void createTest3() {

        assertTrue(abstractFactory.create(ParallelFlowExecutorService.class) instanceof ParallelFlowExecutorServiceImpl);
    }
}