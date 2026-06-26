package com.cigna.tratamientoservice.service;

import com.cigna.tratamientoservice.dto.TratamientoDTO;
import com.cigna.tratamientoservice.model.Tratamiento;
import com.cigna.tratamientoservice.repository.TratamientoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;

    public TratamientoService(TratamientoRepository tratamientoRepository) {
        this.tratamientoRepository = tratamientoRepository;
    }

    public List<TratamientoDTO> listar() {
        return tratamientoRepository.findByActivoTrue()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TratamientoDTO guardar(TratamientoDTO dto) {
        Tratamiento tratamiento = toEntity(dto);
        tratamiento.setActivo(true);
        return toDto(tratamientoRepository.save(tratamiento));
    }

    public TratamientoDTO buscarPorId(Long id) {
        return tratamientoRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tratamiento no encontrado con id: " + id));
    }

    public TratamientoDTO actualizar(Long id, TratamientoDTO dto) {
        Tratamiento existente = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tratamiento no encontrado con id: " + id));
        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setDuracionDias(dto.getDuracionDias());
        return toDto(tratamientoRepository.save(existente));
    }

    public void eliminar(Long id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tratamiento no encontrado con id: " + id));
        tratamiento.setActivo(false);
        tratamientoRepository.save(tratamiento);
    }

    private TratamientoDTO toDto(Tratamiento t) {
        return new TratamientoDTO(t.getIdTratamiento(), t.getNombre(), t.getDescripcion(), t.getDuracionDias(), t.getActivo());
    }

    private Tratamiento toEntity(TratamientoDTO dto) {
        return new Tratamiento(dto.getIdTratamiento(), dto.getNombre(), dto.getDescripcion(), dto.getDuracionDias(), dto.getActivo());
    }
}
