package com.cigna.reservaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDTO {
    private Long idAgenda;
    private Long idUsuario;
    private Long idServicio;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String estado;
}
