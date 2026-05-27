package com.cigna.reservaservice.dto;

import com.cigna.reservaservice.model.Reserva.EstadoReserva;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Long idReserva;

    @NotNull(message = "El id de la agenda es obligatorio")
    private Long idAgenda;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long idUsuario;

    private LocalDateTime fechaReserva;

    private EstadoReserva estado;

    private String observaciones;
}
