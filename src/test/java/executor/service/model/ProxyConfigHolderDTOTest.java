package executor.service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyConfigHolderDTOTest {

    private final String hostname = "localhost";
    private final Integer port = 8080;
    private final String user  = "user";
    private final String password = "password";
    private ProxyConfigHolderDTO proxyConfigHolderDTO;

    @BeforeEach
    public void setUp() {
        proxyConfigHolderDTO = new ProxyConfigHolderDTO(new ProxyNetworkConfigDTO(hostname, port),
                new ProxyCredentialsDTO(user, password));
    }

    @Test
    public void testGetProxyNetworkConfig() {
        assertEquals(new ProxyNetworkConfigDTO(hostname, port), proxyConfigHolderDTO.getProxyNetworkConfig());
    }

    @Test
    public void testSetProxyNetworkConfig() {
        ProxyConfigHolderDTO proxyConfigHolderDTODefault = new ProxyConfigHolderDTO();
        ProxyNetworkConfigDTO proxyNetworkConfigDTO = new ProxyNetworkConfigDTO(hostname, port);
        proxyConfigHolderDTODefault.setProxyNetworkConfig(proxyNetworkConfigDTO);
        assertEquals(proxyNetworkConfigDTO, proxyConfigHolderDTODefault.getProxyNetworkConfig());
    }

    @Test
    public void testGetProxyCredentials() {
        assertEquals(new ProxyCredentialsDTO(user, password), proxyConfigHolderDTO.getProxyCredentials());
    }

    @Test
    public void testSetProxyCredentials() {
        ProxyConfigHolderDTO proxyConfigHolderDTODefault = new ProxyConfigHolderDTO();
        ProxyCredentialsDTO proxyCredentialsDTO = new ProxyCredentialsDTO(user, password);
        proxyConfigHolderDTODefault.setProxyCredentials(proxyCredentialsDTO);
        assertEquals(proxyCredentialsDTO, proxyConfigHolderDTODefault.getProxyCredentials());
    }

    @Test
    public void testEquals() {
        ProxyConfigHolderDTO proxyConfigHolderDTOIdentical = new ProxyConfigHolderDTO();
        proxyConfigHolderDTOIdentical.setProxyNetworkConfig(
                new ProxyNetworkConfigDTO(hostname, port));
        proxyConfigHolderDTOIdentical.setProxyCredentials(
                new ProxyCredentialsDTO(user, password));
        assertEquals(proxyConfigHolderDTOIdentical, proxyConfigHolderDTO);
    }

    @Test
    public void testHashCode() {
        ProxyConfigHolderDTO proxyConfigHolderDTOIdentical = new ProxyConfigHolderDTO(
                new ProxyNetworkConfigDTO(hostname, port), new ProxyCredentialsDTO(user, password)
        );
        assertEquals(proxyConfigHolderDTOIdentical.hashCode(), proxyConfigHolderDTO.hashCode());
    }
}
