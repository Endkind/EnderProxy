package net.endkind.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Config {
    private final Map<String, Map<Integer, ServerConfig>> serverConfig = new HashMap<>();
    private final HashSet<Integer> allListenPorts = new HashSet<>();

    public void addServerConfig(ServerConfig serverConfig) {
        this.serverConfig.put(serverConfig.getDomain(), Map.of(serverConfig.getListenPort(), serverConfig));
        this.allListenPorts.add(serverConfig.getListenPort());
    }

    public ServerConfig getServerConfig(String domain, int port) {
        Map<Integer, ServerConfig> serverConfigs = this.serverConfig.get(domain);
        if (serverConfigs == null) {
            return null;
        }
        return serverConfigs.get(port);
    }

    public HashSet<Integer> getAllListenPorts() {
        return this.allListenPorts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Config{");

        sb.append("serverConfig=");
        sb.append("{");

        for (Map.Entry<String, Map<Integer, ServerConfig>> entry : serverConfig.entrySet()) {
            sb.append(entry.getKey());
            sb.append("={");

            Map<Integer, ServerConfig> innerMap = entry.getValue();
            boolean first = true;
            for (Map.Entry<Integer, ServerConfig> innerEntry : innerMap.entrySet()) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(innerEntry.getKey());
                sb.append(": ");
                sb.append(innerEntry.getValue());
                first = false;
            }

            sb.append("}");
        }

        sb.append("}");

        sb.append("}");

        return sb.toString();
    }
}
