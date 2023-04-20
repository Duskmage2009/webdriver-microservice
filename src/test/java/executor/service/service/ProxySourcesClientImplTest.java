package executor.service.service;

import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import executor.service.service.impl.ProxySourcesClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProxySourcesClientTest {

    private ProxySourcesClientImpl proxySourcesClient;
    private List<ProxyCredentialsDTO> proxyCredentials;
    private List<ProxyNetworkConfigDTO> proxyNetworkConfigs;
    private List<ProxyConfigHolderDTO> proxyConfigHolders;
    private static final String host1 = "host1";
    private static final Integer port2 = 8088;
    private static final String user1 = "user11";
    private static final String password2 = "pass2";

    @BeforeEach
    public void setUpEach() {
        proxySourcesClient = new ProxySourcesClientImpl();
        proxyCredentials = new ArrayList<>();
        proxyNetworkConfigs = new ArrayList<>();
        proxyConfigHolders = new ArrayList<>();
    }

    @Test
    public void testGetProxy() {
        proxyCredentials = proxySourcesClient.readProxyCredentials();
        proxyNetworkConfigs = proxySourcesClient.readProxyNetworks();
        int countOfProxy = Math.min(proxyCredentials.size(), proxyNetworkConfigs.size());
        for(int i = 0; i <= countOfProxy; i++) {
            proxyConfigHolders.add(proxySourcesClient.getProxy());
        }

        assertTrue(proxyConfigHolders.get(0).equals(proxyConfigHolders.get(countOfProxy)));
        assertEquals(host1, proxyConfigHolders.get(countOfProxy).getProxyNetworkConfig().getHostname());
        assertEquals(user1, proxyConfigHolders.get(countOfProxy).getProxyCredentials().getUser());
    }

    @Test
    public void testReadProxyCredentials() {
        proxyCredentials = proxySourcesClient.readProxyCredentials();
        assertEquals(user1, proxyCredentials.get(0).getUser());
        assertEquals(password2, proxyCredentials.get(1).getPassword());
    }

    @Test
    public void testReadProxyNetworks() {
        proxyNetworkConfigs = proxySourcesClient.readProxyNetworks();
        assertEquals(host1, proxyNetworkConfigs.get(0).getHostname());
        assertEquals(port2, proxyNetworkConfigs.get(1).getPort());
    }
}