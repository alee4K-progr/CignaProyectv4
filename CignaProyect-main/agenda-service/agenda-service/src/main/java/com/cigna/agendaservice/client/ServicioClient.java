package com.cigna.agendaservice.client;

import com.cigna.agendaservice.dto.ServicioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ServicioClient {

    private final RestTemplate restTemplate;

    @Value("${api.gateway.url}")
    private String gatewayUrl;

    public ServicioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ServicioDTO obtenerServicio(Long idServicio) {
        try {
            return restTemplate.getForObject(
                gatewayUrl + "/api/servicios/" + idServicio, ServicioDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
