package net.endkind.helper;

import net.endkind.EnderProxy;
import net.endkind.model.Config;
import net.endkind.model.ServerConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ConfigHelper {
    private static final String CONFIG_PATH = "EnderProxy/config.yml";

    public static ServerConfig getServerConfig(String domain, int port) {
        Config config = EnderProxy.getInstance().getConfig();
        return config.getServerConfig(domain, port);
    }

    public static void copyConfigFromResource() {
        try {
            File configFile = new File(CONFIG_PATH);
            File parentDir = configFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (InputStream inputStream = ConfigHelper.class.getClassLoader().getResourceAsStream("config.yml");
                 OutputStream outputStream = new FileOutputStream(configFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

            } catch (IOException e) {
                System.err.println("Error copying config.yml from resources: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    public static Config getConfig() {
        Config config = new Config();

        try (InputStream inputStream = new FileInputStream(CONFIG_PATH)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);

            if (data != null) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    if ("version".equals(entry.getKey())) {
                        continue;
                    }

                    String domain = entry.getKey();

                    for (Object obj: (List) entry.getValue()) {
                        Map<String, Object> serverConfigMap = (Map<String, Object>) obj;
                        int listenPort = (int) serverConfigMap.get("listenPort");
                        String backendHost = (String) serverConfigMap.get("backendHost");
                        int backendPort = (int) serverConfigMap.get("backendPort");

                        ServerConfig serverConfig = new ServerConfig(domain, listenPort, backendHost, backendPort);
                        config.addServerConfig(serverConfig);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading config.yml: " + e.getMessage());
        }

        return config;
    }

    public static void migrateConfig() {
        try {
            Path path = Paths.get(CONFIG_PATH);

            if (!Files.exists(path)) {
                copyConfigFromResource();
            }

            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(Files.newInputStream(path));

            if (config.containsKey("version")) {
                int version = (int) config.get("version");

                switch (version) {
                    case 1:
                        migrateConfigV1ToV2(config);
                        config.put("version", 2);
                        saveConfig(config);
                        break;
                    case 2:
                        break;
                    default:
                        System.err.println("Unknown version: " + version);
                }
            }
        } catch (IOException e) {
            System.err.println("Error migrating config.yml: " + e.getMessage());
        }
    }

    private static void migrateConfigV1ToV2(Map<String, Object> config) {
        for (Map.Entry<String, Object> entry : config.entrySet()) {
            if (entry.getValue() instanceof Map) {
                Map<String, Object> domainConfig = (Map<String, Object>) entry.getValue();

                List<Map<String, Object>> newList = List.of(
                        Map.of(
                                "listenPort", domainConfig.get("listenPort"),
                                "backendHost", domainConfig.get("backendHost"),
                                "backendPort", domainConfig.get("backendPort")
                        )
                );

                config.put(entry.getKey(), newList);
            }
        }
    }

    private static void saveConfig(Map<String, Object> config) throws IOException {
        try {
            Yaml yaml = new Yaml();
            yaml.dump(config, new FileWriter(CONFIG_PATH));
        } catch (IOException e) {
            System.err.println("Error saving config.yml: " + e.getMessage());
        }
    }
}
