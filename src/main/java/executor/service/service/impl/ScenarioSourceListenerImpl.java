package executor.service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.ScenarioDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import executor.service.service.ScenarioSourceListener;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {
    private Queue<ScenarioDTO> scenarioQueue;

    public ScenarioSourceListenerImpl() {
        scenarioQueue = new LinkedList<>();
        loadScenarios();
    }

    @Override
    public synchronized ScenarioDTO getScenario() {
        if (scenarioQueue.isEmpty()) {
            return null;
        }
        return scenarioQueue.poll();
    }

    private void loadScenarios() {
        try (InputStream inputStream = getClass().getResourceAsStream("/scenario.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String json = jsonBuilder.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            List<ScenarioDTO> scenarioDTOs = objectMapper.readValue(json, new TypeReference<List<ScenarioDTO>>(){});
            for (ScenarioDTO scenarioDTO : scenarioDTOs) {
                scenarioQueue.offer(scenarioDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}