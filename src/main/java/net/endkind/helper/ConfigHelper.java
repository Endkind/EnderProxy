package net.endkind.helper;

import net.endkind.EnderProxy;
import net.endkind.model.Config;
import net.endkind.model.ServerConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class ConfigHelper {
    public static ServerConfig getServerConfig(String domain) {
        Config config = EnderProxy.getInstance().getConfig();
        return config.getServerConfig(domain);
    }

    public static void copyConfigFromResource() {
        try (InputStream inputStream = ConfigHelper.class.getClassLoader().getResourceAsStream("config.yml");
             OutputStream outputStream = new FileOutputStream("EnderProxy/config.yml")) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

        } catch (Exception e) {
            System.err.println("Error copying config.yml from resources: " + e.getMessage());
        }
    }

    public static Config getConfig() {
        Config config = new Config();

        try (InputStream inputStream = new FileInputStream("EnderProxy/config.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);

            if (data != null) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    if ("version".equals(entry.getKey())) {
                        continue;
                    }

                    String domain = entry.getKey();
                    Map<String, Object> serverConfigMap = (Map<String, Object>) entry.getValue();
                    int listenPort = (int) serverConfigMap.get("listenPort");
                    String backendHost = (String) serverConfigMap.get("backendHost");
                    int backendPort = (int) serverConfigMap.get("backendPort");

                    ServerConfig serverConfig = new ServerConfig(domain, listenPort, backendHost, backendPort);
                    config.addServerConfig(serverConfig);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading config.yml: " + e.getMessage());
        }

        return config;
    }
}
