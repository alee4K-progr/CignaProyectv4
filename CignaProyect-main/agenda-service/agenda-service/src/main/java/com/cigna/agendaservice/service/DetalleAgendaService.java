package com.cigna.agendaservice.service;

import com.cigna.agendaservice.dto.DetalleAgendaDTO;
import com.cigna.agendaservice.model.Agenda;
import com.cigna.agendaservice.model.DetalleAgenda;
import com.cigna.agendaservice.repository.AgendaRepository;
import com.cigna.agendaservice.repository.DetalleAgendaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleAgendaService {

    private final DetalleAgendaRepository detalleRepo;
    private final AgendaRepository agendaRepo;

    public DetalleAgendaService(DetalleAgendaRepository detalleRepo, AgendaRepository agendaRepo) {
        this.detalleRepo = detalleRepo;
        this.agendaRepo = agendaRepo;
    }

    public List<DetalleAgendaDTO> listarPorAgenda(Long idAgenda) {
        return detalleRepo.findByAgendaIdAgendaAndActivoTrue(idAgenda)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public DetalleAgendaDTO crear(DetalleAgendaDTO dto) {
        Agenda agenda = agendaRepo.findById(dto.getIdAgenda())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda no encontrada"));
        DetalleAgenda detalle = new DetalleAgenda(null, agenda, dto.getIdTratamiento(), dto.getObservaciones(), true);
        return toDto(detalleRepo.save(detalle));
    }

    public DetalleAgendaDTO buscarPorId(Long id) {
        return detalleRepo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle no encontrado"));
    }

    public void eliminar(Long id) {
        DetalleAgenda d = detalleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle no encontrado"));
        d.setActivo(false);
        detalleRepo.save(d);
    }

    private DetalleAgendaDTO toDto(DetalleAgenda d) {
        return new DetalleAgendaDTO(d.getIdDetalle(), d.getAgenda().getIdAgenda(),
                d.getIdTratamiento(), d.getObservaciones(), d.getActivo());
    }
}
