package executor.service.service.impl;

import executor.service.model.StepDTO;
import executor.service.service.StepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StepExecutionClickXpath implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickXpath";
    }

    @Override
    public void step(WebDriver webDriver, StepDTO stepDTO) {

        WebElement webElement = webDriver.findElement(By.xpath(stepDTO.getValue()));
        webElement.click();
    }
}
