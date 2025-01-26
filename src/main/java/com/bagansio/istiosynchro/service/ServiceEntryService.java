package com.bagansio.istiosynchro.service;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceEntryService {

    public String generateServiceEntryYaml(String name, String host, int port, String protocol) {
        Map<String, Object> serviceEntry = new HashMap<>();
        serviceEntry.put("apiVersion", "networking.istio.io/v1alpha3");
        serviceEntry.put("kind", "ServiceEntry");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", name);
        metadata.put("namespace", "learning");
        serviceEntry.put("metadata", metadata);

        Map<String, Object> spec = new HashMap<>();
        spec.put("hosts", new String[]{host});

        Map<String, Object> portMap = new HashMap<>();
        portMap.put("number", port);
        portMap.put("name", protocol.toLowerCase());
        portMap.put("protocol", protocol.toUpperCase());
        spec.put("ports", new Map[]{portMap});

        spec.put("resolution", "DNS");
        spec.put("location", "MESH_EXTERNAL");
        serviceEntry.put("spec", spec);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        return yaml.dump(serviceEntry);
    }
}