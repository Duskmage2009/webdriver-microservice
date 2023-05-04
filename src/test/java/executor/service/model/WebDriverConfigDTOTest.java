package executor.service.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WebDriverConfigDTOTest {
    private WebDriverConfigDTO webDriverConfigDTO = new WebDriverConfigDTO();
    private final Long pageLoadTimeout = 10L;
    private final Long implicitlyWait = 20L;

    @Test
    public void testDefaultConstructor() {
        webDriverConfigDTO = new WebDriverConfigDTO();

        Assertions.assertNull(webDriverConfigDTO.getWebDriverExecutable());
        Assertions.assertNull(webDriverConfigDTO.getUserAgent());
        Assertions.assertNull(webDriverConfigDTO.getImplicitlyWait());
        Assertions.assertNull(webDriverConfigDTO.getPageLoadTimeout());
    }

    @Test
    public void testFullConstructor() {
        String webDriverExecutable = "TestWebDriverExecutable";
        String userAgent ="TestUserAgent";
        webDriverConfigDTO = new WebDriverConfigDTO(webDriverExecutable,
                userAgent,
                pageLoadTimeout,
                implicitlyWait
        );

        Assertions.assertEquals(webDriverConfigDTO.getWebDriverExecutable(), webDriverExecutable);
        Assertions.assertEquals(webDriverConfigDTO.getUserAgent(), userAgent);
        Assertions.assertEquals(webDriverConfigDTO.getPageLoadTimeout(), pageLoadTimeout);
        Assertions.assertEquals(webDriverConfigDTO.getImplicitlyWait(), implicitlyWait);
    }

    @Test
    public void testSetters() {
        webDriverConfigDTO = new WebDriverConfigDTO();
        String testSetterWDE = "TestSetterWDE";
        String userAgent = "TestSetterUA";

        webDriverConfigDTO.setWebDriverExecutable(testSetterWDE);
        webDriverConfigDTO.setUserAgent(userAgent);
        webDriverConfigDTO.setPageLoadTimeout(pageLoadTimeout);
        webDriverConfigDTO.setImplicitlyWait(implicitlyWait);

        Assertions.assertEquals(webDriverConfigDTO.getWebDriverExecutable(), testSetterWDE);
        Assertions.assertEquals(webDriverConfigDTO.getUserAgent(), userAgent);
        Assertions.assertEquals(webDriverConfigDTO.getPageLoadTimeout(), pageLoadTimeout);
        Assertions.assertEquals(webDriverConfigDTO.getImplicitlyWait(), implicitlyWait);
    }

    @Test
    public void testEquals() {
        WebDriverConfigDTO webDriverConfigDTO1 = new WebDriverConfigDTO("WDE1", "UA1", 11L, 21L);
        WebDriverConfigDTO webDriverConfigDTO2 = new WebDriverConfigDTO("WDE2", "UA2", 12L, 22L);

        Assertions.assertEquals(webDriverConfigDTO1, webDriverConfigDTO1);
        Assertions.assertEquals(webDriverConfigDTO2, webDriverConfigDTO2);
        Assertions.assertNotEquals(webDriverConfigDTO1, webDriverConfigDTO2);

        webDriverConfigDTO2.setWebDriverExecutable(webDriverConfigDTO1.getWebDriverExecutable());
        webDriverConfigDTO2.setUserAgent(webDriverConfigDTO1.getUserAgent());
        webDriverConfigDTO2.setImplicitlyWait(webDriverConfigDTO1.getImplicitlyWait());
        webDriverConfigDTO2.setPageLoadTimeout(webDriverConfigDTO1.getPageLoadTimeout());

        Assertions.assertEquals(webDriverConfigDTO1, webDriverConfigDTO2);
    }
}
