package net.endkind.model;

public class ServerConfig {
    private String domain;
    private int listenPort;
    private String backendHost;
    private int backendPort;

    public ServerConfig(String domain, int listenPort, String backendHost, int backendPort) {
        this.domain = domain;
        this.listenPort = listenPort;
        this.backendHost = backendHost;
        this.backendPort = backendPort;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public String getBackendHost() {
        return backendHost;
    }

    public void setBackendHost(String backendHost) {
        this.backendHost = backendHost;
    }

    public int getBackendPort() {
        return backendPort;
    }

    public void setBackendPort(int backendPort) {
        this.backendPort = backendPort;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "domain='" + domain + '\'' +
                ", listenPort=" + listenPort +
                ", backendHost='" + backendHost + '\'' +
                ", backendPort=" + backendPort +
                '}';
    }
}
