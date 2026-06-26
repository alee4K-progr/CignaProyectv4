package com.cigna.agendaservice.repository;

import com.cigna.agendaservice.model.Agenda;
import com.cigna.agendaservice.model.Agenda.EstadoAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    List<Agenda> findByEstado(EstadoAgenda estado);

    List<Agenda> findByIdUsuario(Long idUsuario);

    List<Agenda> findByIdServicio(Long idServicio);

    List<Agenda> findByFechaBetween(LocalDate desde, LocalDate hasta);

    List<Agenda> findByFechaAndEstado(LocalDate fecha, EstadoAgenda estado);
}
