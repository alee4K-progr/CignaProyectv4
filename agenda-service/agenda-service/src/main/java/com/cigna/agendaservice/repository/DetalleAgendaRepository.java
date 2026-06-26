package com.cigna.agendaservice.repository;

import com.cigna.agendaservice.model.DetalleAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleAgendaRepository extends JpaRepository<DetalleAgenda, Long> {
    List<DetalleAgenda> findByAgendaIdAgendaAndActivoTrue(Long idAgenda);
    List<DetalleAgenda> findByIdTratamiento(Long idTratamiento);
}
