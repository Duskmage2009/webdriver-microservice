package executor.service.service;

import executor.service.model.StepDTO;
import executor.service.service.impl.StepExecutionClickCss;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StepExecutionClickCssTest {

    private final String clickCss = "clickCss";

    @Mock
    private WebDriver webDriver;
    @Mock
    private WebElement webElement;
    @Captor
    private ArgumentCaptor<By> selectorCaptor;
    @InjectMocks
    private StepExecutionClickCss stepExecution;
    private StepDTO stepDTO;

    @BeforeEach
    void setUp() {
        stepDTO = new StepDTO(clickCss, "body > ul > li > a");
    }

    @Test
    void shouldBeInitialized() {
        assertNotNull(stepExecution);
    }

    @Test
    void getStepActionShouldReturnClickCss() {
        String actual = stepExecution.getStepAction();
        assertEquals(clickCss, actual);
    }

    @Test
    void webDriverShouldFindWebElement() {
        Mockito.when(webDriver.findElement(Mockito.any())).thenReturn(webElement);
        stepExecution.step(webDriver, stepDTO);
        Mockito.verify(webDriver).findElement(Mockito.any());
    }

    @Test
    void webElementShouldBeClicked() {
        Mockito.when(webDriver.findElement(Mockito.any())).thenReturn(webElement);
        stepExecution.step(webDriver, stepDTO);
        Mockito.verify(webElement).click();
    }

    @Test
    void cssSelectorShouldBeSameAsStepValue() {
        By expectedSelector = By.cssSelector(stepDTO.getValue());
        Mockito.when(webDriver.findElement(Mockito.any())).thenReturn(webElement);

        stepExecution.step(webDriver, stepDTO);

        Mockito.verify(webDriver).findElement(selectorCaptor.capture());
        assertEquals(expectedSelector, selectorCaptor.getValue());
    }

    @Test
    void shouldThrowNoSuchElementWithWrongSelector() {
        String wrongSelector = "Obviously I'm not a selector";
        StepDTO wrongStep = new StepDTO(clickCss, wrongSelector);
        Mockito.when(webDriver.findElement(By.cssSelector(wrongSelector))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> stepExecution.step(webDriver, wrongStep));
    }

    @Test
    void shouldThrowNullPointerWithNullWebDriver() {
        assertThrows(NullPointerException.class, () -> stepExecution.step(null, stepDTO));
    }

    @Test
    void shouldThrowNullPointerWithNullStep() {
        assertThrows(NullPointerException.class, () -> stepExecution.step(webDriver, null));
    }

}