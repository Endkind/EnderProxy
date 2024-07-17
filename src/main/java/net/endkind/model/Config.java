package net.endkind.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Config {
    private Map<String, ServerConfig> serverConfig = new HashMap<>();
    private HashSet<Integer> allListenPorts = new HashSet<>();

    public void addServerConfig(ServerConfig serverConfig) {
        this.serverConfig.put(serverConfig.getDomain(), serverConfig);
        this.allListenPorts.add(serverConfig.getListenPort());
    }

    public ServerConfig getServerConfig(String domain) {
        return this.serverConfig.get(domain);
    }

    public HashSet<Integer> getAllListenPorts() {
        return this.allListenPorts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Config{");
        sb.append("serverConfig=[");
        boolean first = true;
        for (ServerConfig config : serverConfig.values()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(config.toString());
            first = false;
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
}
