package executor.service.service;

import executor.service.model.StepDTO;
import executor.service.service.impl.StepExecutionClickXpath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

class StepExecutionClickXpathTest {

    private final String clickXpath = "clickXpath";
    @Mock
    private WebDriver webDriver;
    private AutoCloseable autoCloseable;
    @Mock
    private WebElement webElement;
    @InjectMocks
    private StepExecutionClickXpath stepExecutionClickXpath;
    private StepDTO stepDTO;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        stepDTO = new StepDTO(clickXpath,"//form/div[1]//div[2]/input");
   }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testInitialize() {

        assertNotNull(stepExecutionClickXpath);
    }

    @Test
    public void testGetStepAction() {

        String actual = stepExecutionClickXpath.getStepAction();
        assertEquals(clickXpath, actual);
    }

    @Test
    public void testFindElement() {

        Mockito.when(webDriver.findElement(By.xpath(stepDTO.getValue()))).thenReturn(webElement);
        stepExecutionClickXpath.step(webDriver, stepDTO);
        Mockito.verify(webDriver).findElement(By.xpath(stepDTO.getValue()));
    }


    @Test
    public void testWebElementClick() {

        Mockito.when(webDriver.findElement(By.xpath(stepDTO.getValue()))).thenReturn(webElement);
        stepExecutionClickXpath.step(webDriver, stepDTO);
        Mockito.verify(webElement).click();
    }

    @Test
    public void testWrongSelector() {

        String value = "Something";
        StepDTO wrongStep = new StepDTO(clickXpath, value);
        Mockito.when(webDriver.findElement(By.xpath(value))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> stepExecutionClickXpath.step(webDriver, wrongStep));
    }
}