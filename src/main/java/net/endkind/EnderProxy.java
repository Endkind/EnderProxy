package net.endkind;

import net.endkind.helper.ConfigHelper;
import net.endkind.model.Config;
import net.endkind.model.ServerConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnderProxy {
    private static EnderProxy instance;
    private Config config;

    public static void main(String[] args) {
        new EnderProxy();
    }

    public EnderProxy() {
        instance = this;

        loadConfig();

        startProxy();
    }

    public void loadConfig() {
        File file = new File("EnderProxy/config.yml");
        File parentDir = file.getParentFile();

        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!file.exists()) {
            ConfigHelper.copyConfigFromResource();
        }

        config = ConfigHelper.getConfig();
    }

    public Config getConfig() {
        return config;
    }

    public static EnderProxy getInstance() {
        return instance;
    }

    public void startProxy() {
        for (int port : ConfigHelper.getConfig().getAllListenPorts()) {
            new Thread(() -> {
                openPort(port);
            }).start();
        }
    }

    private void openPort(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("EnderProxy listening on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Error starting EnderProxy on port " + port + ": " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        new Thread(() ->{
            String clientDomain = clientSocket.getInetAddress().getHostAddress();
            ServerConfig serverConfig = config.getServerConfig(clientDomain);

            if (serverConfig != null) {
                try {
                    System.out.println("Client connected with domain: " + clientDomain);
                    System.out.println("Forwarding to backend: " + serverConfig.getBackendHost() + ":" + serverConfig.getBackendPort());

                    AtomicBoolean isConnected = new AtomicBoolean(true);

                    try (Socket backendSocket = new Socket(serverConfig.getBackendHost(), serverConfig.getBackendPort())) {
                        InputStream clientInput = clientSocket.getInputStream();
                        OutputStream clientOutput = clientSocket.getOutputStream();
                        InputStream backendInput = backendSocket.getInputStream();
                        OutputStream backendOutput = backendSocket.getOutputStream();

                        Thread clientToServer = new Thread(() -> {
                            try {
                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while (isConnected.get() && (bytesRead = clientInput.read(buffer)) != -1) {
                                    backendOutput.write(buffer, 0, bytesRead);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                isConnected.set(false);
                            }
                        });
                        clientToServer.start();

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while (isConnected.get() && (bytesRead = backendInput.read(buffer)) != -1) {
                            clientOutput.write(buffer, 0, bytesRead);
                        }
                        clientToServer.join();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Client connected with unknown domain: " + clientDomain);
            }
        }).start();
    }
}
