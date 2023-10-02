package com.joranbergfeld.airportsystem.arrival;

import com.joranbergfeld.airport_system.airliner.client.api.AirlinerControllerApi;
import com.joranbergfeld.airport_system.gate.client.api.GateControllerApi;
import com.joranbergfeld.airport_system.plane.client.api.PlaneControllerApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiClientsConfiguration {

    private final AppConfigProperties appConfigProperties;

    public ApiClientsConfiguration(AppConfigProperties appConfigProperties) {
        this.appConfigProperties = appConfigProperties;
    }

    @Bean
    GateControllerApi gateControllerApi() {
        com.joranbergfeld.airport_system.gate.client.invoker.ApiClient client = new com.joranbergfeld.airport_system.gate.client.invoker.ApiClient();
        client.setBasePath(appConfigProperties.getClientProtocol() + "://" + appConfigProperties.getGateClient().getUrl() + ":" + appConfigProperties.getGateClient().getPort());
        return new GateControllerApi(client);
    }
    @Bean
    AirlinerControllerApi airlinerControllerApi() {
        com.joranbergfeld.airport_system.airliner.client.invoker.ApiClient client = new com.joranbergfeld.airport_system.airliner.client.invoker.ApiClient();
        client.setBasePath(appConfigProperties.getClientProtocol() + "://" + appConfigProperties.getAirlinerClient().getUrl() + ":" + appConfigProperties.getAirlinerClient().getPort());
        return new AirlinerControllerApi(client);
    }
    @Bean
    PlaneControllerApi planeControllerApi() {
        com.joranbergfeld.airport_system.plane.client.invoker.ApiClient client = new com.joranbergfeld.airport_system.plane.client.invoker.ApiClient();
        client.setBasePath(appConfigProperties.getClientProtocol() + "://" + appConfigProperties.getPlaneClient().getUrl() + ":" + appConfigProperties.getPlaneClient().getPort());
        return new PlaneControllerApi(client);
    }

}
