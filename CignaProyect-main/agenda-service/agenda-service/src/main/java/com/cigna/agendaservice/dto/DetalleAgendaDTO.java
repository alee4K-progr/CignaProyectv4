package com.cigna.agendaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleAgendaDTO {
    private Long idDetalle;
    private Long idAgenda;
    private Long idTratamiento;
    private String observaciones;
    private Boolean activo;
}
