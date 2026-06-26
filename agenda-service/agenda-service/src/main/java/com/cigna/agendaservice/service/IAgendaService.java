package com.cigna.agendaservice.service;

import com.cigna.agendaservice.dto.AgendaDTO;
import com.cigna.agendaservice.model.Agenda.EstadoAgenda;

import java.time.LocalDate;
import java.util.List;

public interface IAgendaService {

    List<AgendaDTO> listar();

    List<AgendaDTO> listarDisponibles();

    List<AgendaDTO> listarPorUsuario(Long idUsuario);

    List<AgendaDTO> listarPorServicio(Long idServicio);

    List<AgendaDTO> listarPorFecha(LocalDate fecha, EstadoAgenda estado);

    AgendaDTO buscarPorId(Long id);

    AgendaDTO crear(AgendaDTO dto);

    AgendaDTO actualizar(Long id, AgendaDTO dto);

    AgendaDTO cambiarEstado(Long id, EstadoAgenda nuevoEstado);

    void eliminar(Long id);
}
