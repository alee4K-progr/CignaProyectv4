package com.cigna.reservaservice.client;

import com.cigna.reservaservice.dto.AgendaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AgendaClient {

    private final RestTemplate restTemplate;

    @Value("${api.gateway.url}")
    private String gatewayUrl;

    public AgendaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AgendaDTO obtenerAgenda(Long idAgenda) {
        try {
            return restTemplate.getForObject(
                gatewayUrl + "/api/agendas/" + idAgenda, AgendaDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
