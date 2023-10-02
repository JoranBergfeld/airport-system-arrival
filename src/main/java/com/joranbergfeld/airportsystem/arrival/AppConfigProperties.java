package com.joranbergfeld.airportsystem.arrival;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "app")
public class AppConfigProperties {
    private String clientProtocol;

    public static class PlaneClient {
        private String url;
        private int port;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static class AirlinerClient {
        private String url;
        private int port;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static class GateClient {
        private String url;
        private int port;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
    private PlaneClient planeClient;
    private AirlinerClient airlinerClient;
    private GateClient gateClient;

    public PlaneClient getPlaneClient() {
        return planeClient;
    }

    public void setPlaneClient(PlaneClient planeClient) {
        this.planeClient = planeClient;
    }

    public AirlinerClient getAirlinerClient() {
        return airlinerClient;
    }

    public void setAirlinerClient(AirlinerClient airlinerClient) {
        this.airlinerClient = airlinerClient;
    }

    public GateClient getGateClient() {
        return gateClient;
    }

    public void setGateClient(GateClient gateClient) {
        this.gateClient = gateClient;
    }

    public String getClientProtocol() {
        return clientProtocol;
    }

    public void setClientProtocol(String clientProtocol) {
        this.clientProtocol = clientProtocol;
    }

}
