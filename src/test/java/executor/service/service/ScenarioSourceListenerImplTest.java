package executor.service.service;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import executor.service.model.ScenarioDTO;
import executor.service.model.StepDTO;
import executor.service.service.impl.ScenarioSourceListenerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScenarioSourceListenerImplTest {

    private ScenarioSourceListenerImpl scenarioSourceListener;

    @BeforeEach
    public void setUp() {
        scenarioSourceListener = new ScenarioSourceListenerImpl();
    }

    @Test
    public void testGetScenarioReturnsScenarioDTO() {
        ScenarioDTO scenarioDTO = scenarioSourceListener.getScenario();
        assertNotNull(scenarioDTO);
        assertEquals(ScenarioDTO.class, scenarioDTO.getClass());
    }

    @Test
    public void testGetScenarioReturnsNullWhenQueueIsEmpty() {
        while (scenarioSourceListener.getScenario() != null) {
        }

        ScenarioDTO scenarioDTO = scenarioSourceListener.getScenario();
        assertNull(scenarioDTO);
    }

    @Test
    public void testLoadScenariosLoadsScenariosIntoQueue() throws Exception {
        Method loadScenariosMethod = ScenarioSourceListenerImpl.class.getDeclaredMethod("loadScenarios");
        loadScenariosMethod.setAccessible(true);
        loadScenariosMethod.invoke(scenarioSourceListener);

        assertNotNull(scenarioSourceListener.getScenario());
    }

    @Test
    public void testScenarioDTOEqualsAndHashCode() {
        StepDTO step1 = new StepDTO("action1", "value1");
        StepDTO step2 = new StepDTO("action2", "value2");
        List<StepDTO> steps1 = new LinkedList<>();
        steps1.add(step1);
        steps1.add(step2);
        List<StepDTO> steps2 = new LinkedList<>();
        steps2.add(step1);
        steps2.add(step2);
        ScenarioDTO scenarioDTO1 = new ScenarioDTO("name1", "site1", steps1);
        ScenarioDTO scenarioDTO2 = new ScenarioDTO("name1", "site1", steps2);

        assertTrue(scenarioDTO1.equals(scenarioDTO2));
        assertTrue(scenarioDTO1.hashCode() == scenarioDTO2.hashCode());
    }
}