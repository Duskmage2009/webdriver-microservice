package executor.service.service.impl;

import executor.service.model.StepDTO;
import executor.service.service.StepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StepExecutionClickCss implements StepExecution {

    @Override
    public String getStepAction() {
        return "clickCss";
    }

    @Override
    public void step(WebDriver webDriver, StepDTO stepDTO) {
        String selector = stepDTO.getValue();
        WebElement element = webDriver.findElement(By.cssSelector(selector));
        element.click();
    }

}
