package ru.otus.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class InfoController {
    private static final String HEALTH_LINK = "http://localhost:8080/actuator/health";
    private static final String MEMORY_LINK ="http://localhost:8080/actuator/metrics/jvm.memory.max";
    private static final String START_TIME_LINK="http://localhost:8080/actuator/metrics/process.start.time";
    private static final String INFO="http://localhost:8080/actuator/info";


    @GetMapping("/info")
    public String getmetric(Model model) {
        Map<String, String> metrics = new HashMap();
        metrics.put("health status",retrieveMetricValue(HEALTH_LINK,"status"));
        metrics.put("jvm memory max ", retrieveMetricValue(MEMORY_LINK,"measurements"));
        metrics.put("process start at", formatEpochMillis(retrieveMetricValue(START_TIME_LINK,"measurements")));
        metrics.put("application name", retrieveMetricValue(INFO,"name"));
        metrics.put("description", retrieveMetricValue(INFO,"description"));
        metrics.put("version", retrieveMetricValue(INFO,"version"));
        model.addAttribute("info", metrics);
        return "info";
    }


    private String retrieveMetricValue(String link, String nameOfMetric) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(link, String.class);
        String valueOfMetric = null;
        try {
            JsonNode jsonNode = mapper.readTree(response.getBody());
            if (jsonNode.get(nameOfMetric) instanceof ArrayNode)  {
                valueOfMetric= jsonNode.get(nameOfMetric).get(0).get("value").asText();
            } else {
                valueOfMetric= jsonNode.get(nameOfMetric).asText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valueOfMetric;
    }


    private String formatEpochMillis(String millis){
        Long longMils = Double.valueOf(millis).longValue()*1000;
        Date date = new Date(longMils);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy HH:mm:ss");
        return sdf.format(date);
    }
}