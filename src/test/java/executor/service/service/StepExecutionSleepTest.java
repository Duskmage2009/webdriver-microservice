package executor.service.service;

import executor.service.model.StepDTO;
import executor.service.service.impl.StepExecutionSleep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StepExecutionSleepTest {

    private final String sleep = "sleep";

    @Mock
    private WebDriver webDriver;

    @InjectMocks
    private StepExecutionSleep stepExecution;

    private StepDTO stepDTO;

    @BeforeEach
    public void setUp() {
        stepDTO = new StepDTO(sleep, "1000");
    }

    @Test
    public void shouldBeInitialized() {
        assertNotNull(stepExecution);
    }

    @Test
    public void getStepActionShouldReturnSleep() {
        String actual = stepExecution.getStepAction();
        assertEquals(sleep, actual);
    }

    @Test
    public void shouldSleepGivenTime() {
        long expectedSleepTime = 1000;

        Instant start = Instant.now();
        stepExecution.step(webDriver, stepDTO);
        Instant end = Instant.now();

        long actualSleepTime = Duration.between(start, end).toMillis();

        assertTrue(actualSleepTime >= expectedSleepTime);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenStepValueIsNotInteger() {
        StepDTO wrongStep = new StepDTO(sleep, "abc");
        assertThrows(IllegalArgumentException.class, () -> stepExecution.step(webDriver, wrongStep));
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenErrorOccurredWhileSleeping() {
        StepDTO wrongStep = new StepDTO(sleep, "-1000");
        assertThrows(RuntimeException.class, () -> stepExecution.step(webDriver, wrongStep));
    }
}