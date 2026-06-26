package com.cigna.reservaservice.repository;

import com.cigna.reservaservice.model.Reserva;
import com.cigna.reservaservice.model.Reserva.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByIdUsuario(Long idUsuario);
    List<Reserva> findByIdAgenda(Long idAgenda);
    List<Reserva> findByEstado(EstadoReserva estado);
    boolean existsByIdAgendaAndEstadoNot(Long idAgenda, EstadoReserva estado);

    // REQ 7: búsqueda por rango de fechas
    List<Reserva> findByFechaReservaBetween(LocalDateTime desde, LocalDateTime hasta);

    // REQ 7: total por usuario
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.idUsuario = :idUsuario")
    long contarPorUsuario(Long idUsuario);

    // REQ 7: total por estado
    long countByEstado(EstadoReserva estado);
}
