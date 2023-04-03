package executor.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProxySourcesClientImpl implements ProxySourcesClient {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static List<ProxyConfigHolderDTO> configs;
    private static int proxyIndex = 0;

    @Override
    public ProxyConfigHolderDTO getProxy() {
        if(configs == null) {
            createProxyConfigHolders();
        }
        if(proxyIndex == configs.size()) {
            proxyIndex = 0;
        }
        ProxyConfigHolderDTO proxyConfigHolder = configs.get(proxyIndex);
        proxyIndex++;
        return proxyConfigHolder;
    }

    public void createProxyConfigHolders() {
        List<ProxyCredentialsDTO> credentials = readProxyCredentials();
        List<ProxyNetworkConfigDTO> networks = readProxyNetworks();
        configs = new ArrayList<>();
        for(int i = 0; i < Math.min(credentials.size(), networks.size()); i++) {
            configs.add(new ProxyConfigHolderDTO(networks.get(i), credentials.get(i)));
        }
    }

    public List<ProxyCredentialsDTO> readProxyCredentials() {
        String credentialsJson = readJsonFileFromClasspath("ProxyCredentials.json");
        List<ProxyCredentialsDTO> credentials;
        try {
            credentials = MAPPER.readValue(credentialsJson, new TypeReference<List<ProxyCredentialsDTO>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error parsing ProxyCredentials.json", e);
        }
        return credentials;
    }

    public List<ProxyNetworkConfigDTO> readProxyNetworks() {
        String networksJson = readJsonFileFromClasspath("ProxyNetwork.json");
        List<ProxyNetworkConfigDTO> networks;
        try {
            networks = MAPPER.readValue(networksJson, new TypeReference<List<ProxyNetworkConfigDTO>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error parsing ProxyNetwork.json", e);
        }
        return networks;
    }

    public String readJsonFileFromClasspath(String filename) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + filename);
            }
            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Error reading " + filename, e);
        }
    }
}
