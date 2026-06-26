package com.cigna.servicioservice.service;

import com.cigna.servicioservice.dto.ServicioDTO;
import com.cigna.servicioservice.model.Servicio;
import com.cigna.servicioservice.repository.ServicioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<ServicioDTO> listar() {
        return servicioRepository.findByEstadoTrue()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ServicioDTO guardar(ServicioDTO dto) {
        Servicio servicio = toEntity(dto);
        if (servicio.getEstado() == null) {
            servicio.setEstado(true);
        }
        return toDto(servicioRepository.save(servicio));
    }

    public ServicioDTO buscarPorId(Long id) {
        return servicioRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado con id: " + id));
    }

    public ServicioDTO actualizar(Long id, ServicioDTO dto) {
        Servicio existente = servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado con id: " + id));
        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        return toDto(servicioRepository.save(existente));
    }

    public void eliminar(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado con id: " + id));
        servicio.setEstado(false);
        servicioRepository.save(servicio);
    }

    private ServicioDTO toDto(Servicio s) {
        return new ServicioDTO(s.getIdServicio(), s.getNombre(), s.getDescripcion(), s.getEstado());
    }

    private Servicio toEntity(ServicioDTO dto) {
        return new Servicio(dto.getIdServicio(), dto.getNombre(), dto.getDescripcion(), dto.getEstado());
    }
}
