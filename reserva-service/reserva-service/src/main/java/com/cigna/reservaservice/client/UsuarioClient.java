package com.cigna.reservaservice.client;

import com.cigna.reservaservice.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class UsuarioClient {

    private final RestTemplate restTemplate;

    @Value("${api.gateway.url}")
    private String gatewayUrl;

    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsuarioDTO obtenerUsuario(Long idUsuario) {
        try {
            return restTemplate.getForObject(
                gatewayUrl + "/api/usuarios/" + idUsuario, UsuarioDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
