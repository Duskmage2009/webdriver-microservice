package executor.service.service;

import executor.service.model.StepDTO;
import org.openqa.selenium.WebDriver;

public class StepExecutionSleep implements StepExecution{

    private static final String ACTION = "sleep";
    @Override
    public String getStepAction() {
        return ACTION;
    }

    @Override
    public void step(WebDriver webDriver, StepDTO stepDTO) {
        try {
            int sleepTime = Integer.parseInt(stepDTO.getValue());
            Thread.sleep(sleepTime);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Sleep argument must be an integer");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while executing sleep step", e);
        }
    }
}
